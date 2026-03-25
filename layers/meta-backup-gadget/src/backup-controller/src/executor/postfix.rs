use std::path::PathBuf;
use std::{io, sync::LazyLock};

use regex::Regex;
use tokio::io::{AsyncBufReadExt as _, BufReader};
use tokio_stream::StreamExt as _;
use tokio_stream::wrappers::LinesStream;

use crate::executor::tail::Tail;

enum State {
    Start,
    From { id: String },
    To { id: String },
    Removed,
}

struct Send {
    state: State,
    from: String,
    to: String,
}

impl Send {
    fn new(from: String, to: String) -> Self {
        Send {
            from,
            to,
            state: State::Start,
        }
    }

    fn input_line(mut self, line: &str) -> Send {
        static QMGR_FROM_RE: LazyLock<Regex> =
            LazyLock::new(|| Regex::new("(?<id>[^\\s]+): from=<(?<from>[^>]+)>").unwrap());
        static SMTP_RE: LazyLock<Regex> =
            LazyLock::new(|| Regex::new("(?<id>[^\\s]+): to=<(?<to>[^>]+)>").unwrap());
        static QMGR_REMOVED_RE: LazyLock<Regex> =
            LazyLock::new(|| Regex::new("(?<id>[^\\s]+): removed").unwrap());

        match &self.state {
            State::Start => {
                if let Some(captures) = QMGR_FROM_RE.captures(line) {
                    // INVARIANT: These capture groups exit in the regex.
                    let from = captures.name("from").unwrap();
                    if from.as_str() == self.from {
                        let id = captures.name("id").unwrap().as_str().to_string();
                        self.state = State::From { id };
                    }
                }
            }

            State::From { id } => {
                let Some(captures) = SMTP_RE.captures(line) else {
                    return self;
                };
                // INVARIANT: These capture groups exist in the regex.
                let to = captures.name("to").unwrap();
                let line_id = captures.name("id").unwrap();
                if to.as_str() == self.to && line_id.as_str() == id {
                    self.state = State::To { id: id.to_string() };
                }
            }

            State::To { id } => {
                let Some(captures) = QMGR_REMOVED_RE.captures(line) else {
                    return self;
                };

                // INVARIANT: This capture group exists in the regex.
                let line_id = captures.name("id").unwrap();
                if line_id.as_str() == id {
                    self.state = State::Removed;
                }
            }
            State::Removed => {}
        };

        self
    }
}

/// This method only works if the message is sent directly to the recipient. If the mail is sent to
/// a forwarded address, there will be multiple qmgr/smtp sequences, and this state machine will
/// not reach its final state when the forwarded message is delivered (as is likely intended).
///
/// Additionally, this method only works with the busybox syslogd implementation.
pub async fn completed_send(from: String, to: String) -> io::Result<()> {
    let mut send = Send::new(from, to);
    let mut file = LinesStream::new(
        BufReader::new(Tail::new(PathBuf::from("/var/log/messages")).await?).lines(),
    );
    while let Some(line) = file.next().await {
        send = send.input_line(&line?);
        if matches!(send.state, State::Removed) {
            return Ok(());
        }
    }
    // We should never get here
    unreachable!()
}

#[cfg(test)]
mod test {
    use super::*;

    fn drive_test<'a>(lines: impl Iterator<Item = &'a str>) {
        let from = "backup@backup.ethantwardy.com".to_string();
        let to = "et@ethantwardy.com".to_string();
        let result = lines.fold(Send::new(from, to), Send::input_line);
        assert!(matches!(result.state, State::Removed));
    }

    #[test]
    fn connection_refused() {
        let input = vec![
            "Mar 22 07:45:01 backup mail.info postfix/smtpd[1080]: connect from localhost[::1]",
            "Mar 22 07:45:01 backup mail.info postfix/smtpd[1080]: 8095C171A: client=localhost[::1]",
            "Mar 22 07:45:01 backup mail.info postfix/cleanup[1083]: 8095C171A: message-id=<20260322124501.8095C171A@backup.ethantwardy.com>",
            "Mar 22 07:45:01 backup mail.info postfix/smtpd[1080]: disconnect from localhost[::1] ehlo=1 mail=1 rcpt=1 data=1 quit=1 commands=5",
            "Mar 22 07:45:01 backup mail.info postfix/cleanup[1083]: 8DB5E171B: message-id=<20260322124501.8095C171A@backup.ethantwardy.com>",
            "Mar 22 07:45:01 backup mail.info postfix/qmgr[595]: 8DB5E171B: from=<backup@backup.ethantwardy.com>, size=633, nrcpt=1 (queue active)",
            "Mar 22 07:45:01 backup mail.info postfix/smtp[1085]: connect to 10.4.0.1[10.4.0.1]:25: Connection refused",
            "Mar 22 07:45:01 backup mail.info postfix/smtp[1085]: 8DB5E171B: to=<et@ethantwardy.com>, orig_to=<root@backup.ethantwardy.com>, relay=none, delay=0.04, delays=0.01/0.02/0.01/0, dsn=4.4.1, status=deferred (connect to 10.4.0.1[10.4.0.1]:25: Connection refused)",
            "Mar 22 11:30:52 backup mail.info postfix/qmgr[595]: 8DB5E171B: from=<backup@backup.ethantwardy.com>, size=633, nrcpt=1 (queue active)",
            "Mar 22 11:31:04 backup mail.info postfix/smtp[1091]: 8DB5E171B: to=<et@ethantwardy.com>, orig_to=<root@backup.ethantwardy.com>, relay=10.4.0.1[10.4.0.1]:25, delay=13562, delays=13551/0.02/11/0.4, dsn=2.0.0, status=sent (250 2.0.0 Ok: queued as A96957B976)",
            "Mar 22 11:31:04 backup mail.info postfix/qmgr[595]: 8DB5E171B: removed",
        ];

        drive_test(input.into_iter());
    }

    #[test]
    fn successful() {
        let input = vec![
            "Mar 22 04:46:47 backup mail.info postfix/smtpd[1038]: connect from localhost[::1]",
            "Mar 22 04:46:47 backup mail.info postfix/smtpd[1038]: 5F7281717: client=localhost[::1]",
            "Mar 22 04:46:47 backup mail.info postfix/cleanup[1041]: 5F7281717: message-id=<20260322094647.5F7281717@backup.ethantwardy.com>",
            "Mar 22 04:46:47 backup mail.info postfix/smtpd[1038]: disconnect from localhost[::1] ehlo=1 mail=1 rcpt=1 data=1 quit=1 commands=5",
            "Mar 22 04:46:47 backup mail.info postfix/cleanup[1041]: 6E89C1718: message-id=<20260322094647.5F7281717@backup.ethantwardy.com>",
            "Mar 22 04:46:47 backup mail.info postfix/qmgr[595]: 6E89C1718: from=<backup@backup.ethantwardy.com>, size=10072, nrcpt=1 (queue active)",
            "Mar 22 04:46:47 backup mail.info postfix/smtp[1043]: 6E89C1718: to=<et@ethantwardy.com>, orig_to=<root@backup.ethantwardy.com>, relay=10.4.0.1[10.4.0.1]:25, delay=0.54, delays=0.01/0.09/0.1/0.34, dsn=2.0.0, status=sent (250 2.0.0 Ok: queued as A57B97B77D)",
            "Mar 22 04:46:47 backup mail.info postfix/qmgr[595]: 6E89C1718: removed",
        ];

        drive_test(input.into_iter());
    }
}
