use std::{
    fs,
    io::{self, Write},
    path::PathBuf,
    process::{Command, Output},
    thread::sleep,
    time::Duration,
};

use chrono::{DateTime, Datelike, Local, NaiveTime, TimeDelta, Weekday};
use lettre::{
    Message, SmtpTransport, Transport,
    message::{Mailbox, SinglePart},
    transport::smtp,
};
use log::info;

enum Schedule {
    Daily(NaiveTime),
    Weekly { weekday: Weekday, time: NaiveTime },
}

struct Job {
    command: Box<dyn Fn() -> Command>,
    schedule: Schedule,
}

#[derive(Debug, thiserror::Error)]
enum CommandError {
    #[error("executing rtcwake: {0}")]
    Io(#[from] io::Error),
    #[error("{0} failed")]
    SubprocessError(&'static str),
    #[error("formatting message: {0}")]
    Message(#[from] lettre::error::Error),
    #[error("sending email: {0}")]
    Smtp(#[from] smtp::Error),
}

fn jobs() -> Vec<Job> {
    vec![
        Job {
            command: Box::new(|| {
                let mut btrbk = Command::new("/usr/bin/btrbk");
                btrbk.args(["run"]);
                btrbk
            }),
            schedule: Schedule::Daily("04:45:00".parse().unwrap()),
        },
        Job {
            command: Box::new(|| Command::new("/usr/libexec/backup-databases.sh")),
            schedule: Schedule::Weekly {
                weekday: Weekday::Sun,
                time: "07:45:00".parse().unwrap(),
            },
        },
    ]
}

fn next_invocation(after: &DateTime<Local>, job: &Job) -> DateTime<Local> {
    let time_until_invocation_tomorrow = |time: NaiveTime| {
        if time > after.time() {
            time - after.time()
        } else {
            TimeDelta::hours(24) + time.signed_duration_since(after.time())
        }
    };

    let time_until_next = match job.schedule {
        Schedule::Daily(time) => time_until_invocation_tomorrow(time),
        Schedule::Weekly { weekday, time } => {
            let hours = time_until_invocation_tomorrow(time);
            let weekday = weekday.num_days_from_sunday();
            let today = after.weekday().num_days_from_sunday();
            if today == weekday {
                if time > after.time() {
                    hours
                } else {
                    hours + TimeDelta::days((6 - (today - weekday)).into())
                }
            } else if today < weekday {
                hours + TimeDelta::days((weekday - today).into())
            } else {
                hours + TimeDelta::days((7 - (today - weekday)).into())
            }
        }
    };

    *after + time_until_next
}

fn next_job<'a>(from: &DateTime<Local>, jobs: &'a [Job]) -> Option<&'a Job> {
    let mut iter = jobs.iter();
    let init = iter.next()?;
    Some(iter.fold(init, |a, b| {
        if next_invocation(from, a) < next_invocation(from, b) {
            a
        } else {
            b
        }
    }))
}

fn is_job_due_within(delta: &TimeDelta, job: &Job) -> bool {
    let now = Local::now();
    next_invocation(&now, job) < (now + *delta)
}

fn sleep_until(until: DateTime<Local>) {
    sleep((until - Local::now()).to_std().unwrap());
}

fn hibernate(until: &DateTime<Local>) -> Result<(), CommandError> {
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
            false => Err(CommandError::SubprocessError("rtcwake")),
        }
    }
}

struct Mailer {
    transport: SmtpTransport,
    recipient: Mailbox,
    sender: Mailbox,
}

impl Mailer {
    fn new(transport: SmtpTransport, domain: &str, recipient_username: &str) -> Self {
        Self {
            transport,
            recipient: format!("{}@{}", recipient_username, domain)
                .parse()
                .unwrap(),
            sender: format!("backup@{}", domain).parse().unwrap(),
        }
    }

    fn run_job(&self, job: &Job) -> Result<(), CommandError> {
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
}

fn disable_wakeup_sources() -> io::Result<()> {
    fs::write("/proc/acpi/wakeup", b"XHC0")
}

fn main() {
    env_logger::builder()
        .format(|buf, record| writeln!(buf, "[{}] {}", record.level(), record.args()))
        .init();

    disable_wakeup_sources().expect("failed to disable wakeup sources");
    let domain = fs::read_to_string("/etc/hostname").expect("failed to read hostname");

    let jobs = jobs();
    let mailer = Mailer::new(
        SmtpTransport::unencrypted_localhost(),
        domain.trim_end(),
        "root",
    );

    // Stay awake for the first minute, allowing the user to log in and create /etc/caffeinated, if
    // they so desire.
    sleep(Duration::from_secs(60));

    const WAKEUP_BUFFER: TimeDelta = TimeDelta::seconds(15);
    loop {
        let now = Local::now();
        let next_job = next_job(&now, &jobs).unwrap();

        let due_at = next_invocation(&now, &next_job);
        let waketime = due_at.checked_sub_signed(WAKEUP_BUFFER).unwrap();
        hibernate(&waketime).expect("failed to hibernate");

        if is_job_due_within(&WAKEUP_BUFFER, next_job) {
            sleep_until(due_at);
            mailer.run_job(next_job).expect("failed to run job");
        } else {
            // If the job is not due within 60 seconds, this is a spurious wakeup. Keep the system
            // awake for 60 seconds, and then hibernate again.
            sleep(Duration::from_secs(60));
        }
    }
}

#[cfg(test)]
mod test {
    use super::*;

    #[test]
    fn daily_job_lt() {
        assert_eq!(
            "2026-03-15T04:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            next_invocation(
                &"2026-03-15T02:07:47-05:00".parse().unwrap(),
                &Job {
                    command: Box::new(|| Command::new("ls")),
                    schedule: Schedule::Daily("04:45:00".parse().unwrap()),
                }
            )
        )
    }

    #[test]
    fn daily_job_gt() {
        assert_eq!(
            "2026-03-16T04:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            next_invocation(
                &"2026-03-15T04:45:07-05:00".parse().unwrap(),
                &Job {
                    command: Box::new(|| Command::new("ls")),
                    schedule: Schedule::Daily("04:45:00".parse().unwrap()),
                }
            )
        )
    }

    #[test]
    fn daily_job_eq() {
        assert_eq!(
            "2026-03-16T04:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            next_invocation(
                &"2026-03-15T04:45:00-05:00".parse().unwrap(),
                &Job {
                    command: Box::new(|| Command::new("ls")),
                    schedule: Schedule::Daily("04:45:00".parse().unwrap()),
                }
            )
        )
    }

    #[test]
    fn weekly_job_lt_sameday() {
        assert_eq!(
            "2026-03-22T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            next_invocation(
                &"2026-03-16T07:43:47-05:00".parse().unwrap(),
                &Job {
                    command: Box::new(|| Command::new("/usr/libexec/backup-databases.sh")),
                    schedule: Schedule::Weekly {
                        weekday: Weekday::Sun,
                        time: "07:45:00".parse().unwrap(),
                    }
                }
            )
        )
    }

    #[test]
    fn weekly_job_lt() {
        assert_eq!(
            "2026-03-16T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            next_invocation(
                &"2026-03-15T04:02:23-05:00".parse().unwrap(),
                &Job {
                    command: Box::new(|| Command::new("ls")),
                    schedule: Schedule::Weekly {
                        weekday: Weekday::Mon,
                        time: "07:45:00".parse().unwrap()
                    },
                }
            )
        )
    }

    #[test]
    fn weekly_job_gt() {
        assert_eq!(
            "2026-03-23T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            next_invocation(
                &"2026-03-16T07:45:23-05:00".parse().unwrap(),
                &Job {
                    command: Box::new(|| Command::new("ls")),
                    schedule: Schedule::Weekly {
                        weekday: Weekday::Mon,
                        time: "07:45:00".parse().unwrap()
                    },
                }
            )
        )
    }

    #[test]
    fn weekly_job_eq() {
        assert_eq!(
            "2026-03-23T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            next_invocation(
                &"2026-03-16T07:45:00-05:00".parse().unwrap(),
                &Job {
                    command: Box::new(|| Command::new("ls")),
                    schedule: Schedule::Weekly {
                        weekday: Weekday::Mon,
                        time: "07:45:00".parse().unwrap()
                    },
                }
            )
        )
    }
}
