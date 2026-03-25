use std::{
    path::Path,
    sync::{Arc, Mutex},
    task::{Poll, Waker},
};

use notify::{INotifyWatcher, RecursiveMode, Watcher as _};

#[derive(Debug)]
enum State {
    Pending(Waker),
    Ready(Vec<notify::Result<notify::Event>>),
}

struct NotifyEventHandler(Arc<Mutex<Option<State>>>);
impl notify::EventHandler for NotifyEventHandler {
    fn handle_event(&mut self, event: notify::Result<notify::Event>) {
        let mut context = self.0.lock().unwrap();
        // TODO: Do we need to handle missed events in here somehow?
        match context.take() {
            Some(State::Pending(waker)) => {
                *context = Some(State::Ready(vec![event]));
                waker.wake();
            }
            Some(State::Ready(mut events)) => {
                events.push(event);
                *context = Some(State::Ready(events));
            }
            s => *context = s,
        }
    }
}

/// A future that becomes ready when an inotify event is received on a watched file. Create the
/// future, and continuously await it to receive events.
pub struct NotifyFuture {
    state: Arc<Mutex<Option<State>>>,
    _watcher: INotifyWatcher,
}
impl NotifyFuture {
    /// Construct a [NotifyFuture] that watches `path`.
    pub fn new(path: &Path) -> Result<Self, notify::Error> {
        let state = Arc::new(Mutex::new(None));
        let event_handler = NotifyEventHandler(state.clone());
        let mut watcher = INotifyWatcher::new(event_handler, Default::default())?;
        watcher.watch(path, RecursiveMode::NonRecursive)?;
        Ok(Self {
            _watcher: watcher,
            state,
        })
    }
}
impl Future for NotifyFuture {
    type Output = Vec<notify::Result<notify::Event>>;

    fn poll(
        self: std::pin::Pin<&mut Self>,
        cx: &mut std::task::Context<'_>,
    ) -> std::task::Poll<Self::Output> {
        let mut state = self.state.lock().unwrap();
        if let Some(State::Ready(events)) = state.take() {
            Poll::Ready(events)
        } else {
            *state = Some(State::Pending(cx.waker().clone()));
            Poll::Pending
        }
    }
}
