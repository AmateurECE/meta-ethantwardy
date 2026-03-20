use std::{
    fs, io,
    path::PathBuf,
    process::{Command, Output},
    thread::sleep,
    time::Duration,
};

use chrono::{DateTime, Local, TimeDelta};
use lettre::{
    Message, SmtpTransport, Transport as _,
    message::{Mailbox, SinglePart},
    transport::smtp,
};
use log::info;

use crate::job::{Job, next_job};

#[derive(Debug, thiserror::Error)]
pub enum Error {
    #[error("executing rtcwake: {0}")]
    Io(#[from] io::Error),
    #[error("{0} failed")]
    SubprocessError(&'static str),
    #[error("formatting message: {0}")]
    Message(#[from] lettre::error::Error),
    #[error("sending email: {0}")]
    Smtp(#[from] smtp::Error),
}

pub fn sleep_until(until: DateTime<Local>) {
    sleep((until - Local::now()).to_std().unwrap());
}

pub fn hibernate(until: &DateTime<Local>) -> Result<(), Error> {
    if fs::exists("/etc/caffeinated")? {
        info!("Not hibernating because /etc/caffeinated exists");
        info!("Sleeping until {}", until);
        sleep(until.signed_duration_since(Local::now()).to_std().unwrap());
        Ok(())
    } else {
        let sleeptime = until.timestamp().to_string();
        info!("Hibernating until {}", until);
        match Command::new("/usr/sbin/rtcwake")
            .args(["-m", "mem", "-t", &sleeptime])
            .status()?
            .success()
        {
            true => Ok(()),
            false => Err(Error::SubprocessError("rtcwake")),
        }
    }
}

pub struct Executor {
    transport: SmtpTransport,
    recipient: Mailbox,
    sender: Mailbox,
}

impl Executor {
    pub fn new(transport: SmtpTransport, domain: &str, recipient_username: &str) -> Self {
        Self {
            transport,
            recipient: format!("{}@{}", recipient_username, domain)
                .parse()
                .unwrap(),
            sender: format!("backup@{}", domain).parse().unwrap(),
        }
    }

    fn run_job(&self, job: &Job) -> Result<(), Error> {
        let mut command = (job.command)();
        let program = PathBuf::from(command.get_program());
        info!("Running {}", program.display());
        let Output {
            stderr: mut body,
            mut stdout,
            ..
        } = command.output()?;
        body.append(&mut stdout);

        let subject = program.file_name().unwrap().to_string_lossy();
        let message = Message::builder()
            .from(self.sender.clone())
            .to(self.recipient.clone())
            .subject(subject)
            .singlepart(SinglePart::plain(body))?;

        info!("Sending message to {}", &self.recipient);
        self.transport.send(&message)?;
        // FIXME: Hard-coded sleep ensures that the MTA has time to send the message.
        sleep(Duration::from_secs(5));
        Ok(())
    }

    pub fn run(self, jobs: &[Job]) -> ! {
        // Stay awake for the first minute, allowing the user to log in and create /etc/caffeinated, if
        // they so desire.
        sleep(Duration::from_secs(60));

        const WAKEUP_BUFFER: TimeDelta = TimeDelta::seconds(15);
        loop {
            let now = Local::now();
            let next_job = next_job(&now, &jobs).unwrap();

            let due_at = next_job.next_invocation(&now);
            let waketime = due_at.checked_sub_signed(WAKEUP_BUFFER).unwrap();
            hibernate(&waketime).expect("failed to hibernate");

            if next_job.is_due_within(&WAKEUP_BUFFER) {
                sleep_until(due_at);
                self.run_job(next_job).expect("failed to run job");
            } else {
                // If the job is not due within 60 seconds, this is a spurious wakeup. Keep the system
                // awake for 60 seconds, and then hibernate again.
                sleep(Duration::from_secs(60));
            }
        }
    }
}
