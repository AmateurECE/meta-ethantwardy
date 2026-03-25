use std::{
    env, fs,
    io::{self, Write as _},
    process::Command,
};

use chrono::Weekday;
use lettre::AsyncSmtpTransport;

use crate::{
    executor::Executor,
    job::{Job, Schedule},
};

mod executor;
mod job;

const MOUNTPOINT: &str = "/dataset";

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
        Job {
            command: Box::new(|| {
                let mut btrfs = Command::new("/usr/bin/btrfs");
                btrfs.args(["scrub", "start", "-Bd", "-c2", "-n4", MOUNTPOINT]);
                btrfs
            }),
            schedule: Schedule::Monthly {
                day_of_month: 1,
                time: "02:00:00".parse().unwrap(),
            },
        },
        Job {
            command: Box::new(|| {
                let mut btrfs = Command::new("/usr/bin/btrfs");
                btrfs.args(["balance", "start", "-v", "-dusage=10", MOUNTPOINT]);
                btrfs
            }),
            schedule: Schedule::Weekly {
                weekday: Weekday::Mon,
                time: "02:00:00".parse().unwrap(),
            },
        },
    ]
}

fn disable_wakeup_sources() -> io::Result<()> {
    fs::write("/proc/acpi/wakeup", b"XHC0")
}

#[tokio::main(flavor = "current_thread")]
async fn main() {
    env_logger::builder()
        .format(|buf, record| writeln!(buf, "[{}] {}", record.level(), record.args()))
        .init();

    disable_wakeup_sources().expect("failed to disable wakeup sources");
    let domain = fs::read_to_string("/etc/hostname").expect("failed to read hostname");

    let recipient = env::var("MAILTO").unwrap();

    let jobs = jobs();
    let executor = Executor::new(
        AsyncSmtpTransport::<_>::unencrypted_localhost(),
        &recipient,
        &format!("backup@{}", domain),
    );

    executor.run(&jobs).await;
}
