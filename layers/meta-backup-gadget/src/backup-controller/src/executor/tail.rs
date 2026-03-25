use std::io::{Seek as _, SeekFrom};
use std::path::PathBuf;
use std::task::Poll;
use std::{future::Future, pin::pin};

use tokio::{fs, io};

use crate::executor::notify::NotifyFuture;

#[derive(PartialEq, Eq)]
enum State {
    Unchanged,
    Reading,
}

/// This type provides an impl of [tokio::io::AsyncRead] that is ready whenever data is written to
/// the file. This works kind of like `tail -f -n0`. It uses inotify to detect modifications to the
/// file.
#[pin_project::pin_project]
pub struct Tail {
    path: PathBuf,
    last_location: u64,
    // Present only when state == Unchanged
    #[pin]
    notify: Option<NotifyFuture>,
    // Present only when state == Reading
    #[pin]
    file: Option<fs::File>,
    state: State,
}

impl Tail {
    pub async fn new(path: PathBuf) -> io::Result<Self> {
        Ok(Self {
            last_location: fs::metadata(&path).await?.len(),
            // TODO: Consider returning an error here?
            state: State::Unchanged,
            notify: Some(NotifyFuture::new(&path).unwrap()),
            file: None,
            path,
        })
    }
}

fn error_notify_to_io(error: notify::Error) -> io::Error {
    match error.kind {
        notify::ErrorKind::Generic(msg) => io::Error::other(msg),
        notify::ErrorKind::Io(error) => error,
        notify::ErrorKind::PathNotFound => io::Error::new(io::ErrorKind::NotFound, error),
        notify::ErrorKind::WatchNotFound => io::Error::new(io::ErrorKind::NotFound, error),
        notify::ErrorKind::InvalidConfig(_) => io::Error::new(io::ErrorKind::InvalidInput, error),
        notify::ErrorKind::MaxFilesWatch => io::Error::new(io::ErrorKind::QuotaExceeded, error),
    }
}

fn poll_event(event: notify::Result<notify::Event>) -> Poll<io::Result<()>> {
    match event {
        Ok(event) => match event.kind {
            notify::EventKind::Access(_) => Poll::Pending,
            notify::EventKind::Remove(_) => {
                Poll::Ready(Err(io::Error::new(io::ErrorKind::NotFound, "file removed")))
            }
            _ => Poll::Ready(Ok(())),
        },
        Err(error) => Poll::Ready(Err(error_notify_to_io(error))),
    }
}

trait PollMonadExt<T> {
    fn and_then<U, F>(self, op: F) -> Poll<U>
    where
        F: FnOnce(T) -> Poll<U>;
    fn or(self, other: Self) -> Poll<T>;
}

impl<T> PollMonadExt<T> for Poll<T> {
    fn and_then<U, F>(self, op: F) -> Poll<U>
    where
        F: FnOnce(T) -> Poll<U>,
    {
        match self {
            Poll::Ready(t) => op(t),
            Poll::Pending => Poll::Pending,
        }
    }

    fn or(self, other: Self) -> Poll<T> {
        match self {
            Poll::Ready(_) => self,
            Poll::Pending => other,
        }
    }
}

impl io::AsyncRead for Tail {
    fn poll_read(
        self: std::pin::Pin<&mut Self>,
        cx: &mut std::task::Context<'_>,
        buf: &mut io::ReadBuf<'_>,
    ) -> Poll<std::io::Result<()>> {
        let mut this = self.project();
        let mut waiting = false;
        let mut result = Poll::Pending;
        while !waiting {
            let state_was_reading = *this.state == State::Reading;
            let start_fill = buf.filled().len();
            result = match this.state {
                State::Unchanged => unsafe {
                    this.notify
                        .as_mut()
                        .map_unchecked_mut(|n| n.as_mut().unwrap())
                }
                .poll(cx)
                .and_then(|events| {
                    events
                        .into_iter()
                        .map(poll_event)
                        .reduce(PollMonadExt::or)
                        .unwrap_or(Poll::Pending)
                })
                .and_then(|ready| {
                    let file = ready.and_then(|_| {
                        // TODO: Bad practice to perform these blocking operations within the
                        // future.
                        let new_length = std::fs::metadata(&this.path)?.len();
                        if new_length > *this.last_location {
                            let mut file = std::fs::File::open(&this.path)?;
                            file.seek(SeekFrom::Start(*this.last_location))?;
                            Ok(Some(fs::File::from_std(file)))
                        } else {
                            Ok(None)
                        }
                    });

                    match file {
                        Err(e) => Poll::Ready(Err(e)),
                        Ok(None) => Poll::Pending,
                        Ok(Some(file)) => {
                            this.file.as_mut().replace(file);
                            *this.state = State::Reading;
                            unsafe {
                                this.file
                                    .as_mut()
                                    .map_unchecked_mut(|f| f.as_mut().unwrap())
                            }
                            .poll_read(cx, buf)
                        }
                    }
                }),
                State::Reading => unsafe {
                    this.file
                        .as_mut()
                        .map_unchecked_mut(|f| f.as_mut().unwrap())
                }
                .poll_read(cx, buf),
            };

            let bytes_read = buf.filled().len() - start_fill;
            *this.last_location += u64::try_from(bytes_read).unwrap();
            if state_was_reading
                && matches!(result, Poll::Ready(Ok(())))
                && bytes_read == 0
                && buf.remaining() != 0
            {
                // We've reached EOF, transition back to the Unchanged state.
                *this.state = State::Unchanged;
            } else {
                waiting = true;
            }
        }

        result
    }
}
