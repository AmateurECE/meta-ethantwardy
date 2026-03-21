use std::process::Command;

use chrono::{DateTime, Datelike as _, Local, NaiveTime, TimeDelta, TimeZone, Weekday};

pub enum Schedule {
    Daily(NaiveTime),
    Weekly { weekday: Weekday, time: NaiveTime },
    Monthly { day_of_month: u32, time: NaiveTime },
}

pub struct Job {
    pub command: Box<dyn Fn() -> Command>,
    pub schedule: Schedule,
}

impl Job {
    pub fn next_invocation<Tz: TimeZone>(&self, after: &DateTime<Tz>) -> DateTime<Tz> {
        let time_until_invocation_tomorrow = |time: NaiveTime| {
            if time > after.time() {
                time - after.time()
            } else {
                TimeDelta::hours(24) + time.signed_duration_since(after.time())
            }
        };

        let time_until_next = match self.schedule {
            Schedule::Daily(time) => time_until_invocation_tomorrow(time),
            Schedule::Weekly { weekday, time } => {
                let hours = time_until_invocation_tomorrow(time);
                let weekday = weekday.num_days_from_sunday();
                let today = after.weekday().num_days_from_sunday();
                if today == weekday {
                    if time > after.time() {
                        hours
                    } else {
                        hours + TimeDelta::days((7 - (today - weekday) - 1).into())
                    }
                } else if today < weekday {
                    hours + TimeDelta::days((weekday - today).into())
                } else {
                    hours + TimeDelta::days((7 - (today - weekday)).into())
                }
            }

            Schedule::Monthly { day_of_month, time } => {
                let hours = time_until_invocation_tomorrow(time);
                if after.day() == day_of_month {
                    // It's today! When??
                    if time > after.time() {
                        // It's so close!
                        hours
                    } else {
                        // Oh, no actually it's next month
                        hours
                            + TimeDelta::days(
                                (u32::from(after.num_days_in_month()) - after.day() + day_of_month
                                    - 1)
                                .into(),
                            )
                    }
                } else if after.day() < day_of_month {
                    // Not yet, but it's coming up!
                    hours + TimeDelta::days((day_of_month - after.day()).into())
                } else {
                    // It's next month!
                    hours
                        + TimeDelta::days(
                            (u32::from(after.num_days_in_month()) - after.day() + day_of_month - 1)
                                .into(),
                        )
                }
            }
        };

        after.clone() + time_until_next
    }

    pub fn is_due_within(&self, delta: &TimeDelta) -> bool {
        let now = Local::now();
        self.next_invocation(&now) < (now + *delta)
    }
}

pub fn next_job<'a>(after: &DateTime<Local>, jobs: &'a [Job]) -> Option<&'a Job> {
    let mut iter = jobs.iter();
    let init = iter.next()?;
    Some(iter.fold(init, |a, b| {
        if a.next_invocation(after) < b.next_invocation(after) {
            a
        } else {
            b
        }
    }))
}

#[cfg(test)]
mod test {
    use chrono::{FixedOffset, TimeZone};

    use super::*;

    const OFFSET: i32 = 5 * 3600;

    #[test]
    fn daily_job_lt() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 15, 4, 45, 0)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Daily("04:45:00".parse().unwrap()),
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 15, 02, 07, 47)
                    .unwrap()
            )
        )
    }

    #[test]
    fn daily_job_gt() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 16, 4, 45, 0)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Daily("04:45:00".parse().unwrap()),
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 15, 04, 45, 07)
                    .unwrap()
            )
        )
    }

    #[test]
    fn daily_job_eq() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 16, 4, 45, 0)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Daily("04:45:00".parse().unwrap()),
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 15, 04, 45, 0)
                    .unwrap()
            )
        )
    }

    #[test]
    fn weekly_job_lt_sameday() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 22, 07, 45, 0)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("/usr/libexec/backup-databases.sh")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Sun,
                    time: "07:45:00".parse().unwrap(),
                }
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 16, 7, 43, 47)
                    .unwrap()
            )
        )
    }

    #[test]
    fn weekly_job_lt() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 16, 07, 45, 0)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Mon,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 15, 04, 02, 23)
                    .unwrap()
            )
        )
    }

    #[test]
    fn weekly_job_gt() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 23, 07, 45, 0)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Mon,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 16, 7, 45, 23)
                    .unwrap()
            )
        )
    }

    #[test]
    fn weekly_job_eq() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 23, 7, 45, 0)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Mon,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 16, 07, 45, 00)
                    .unwrap()
            )
        )
    }

    #[test]
    fn monthly_job_lt_sameday() {
        assert_eq!(
            FixedOffset::west_opt(5 * 3600)
                .unwrap()
                .with_ymd_and_hms(2026, 03, 01, 07, 45, 00)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("/usr/libexec/backup-databases.sh")),
                schedule: Schedule::Monthly {
                    day_of_month: 1,
                    time: "07:45:00".parse().unwrap(),
                }
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 01, 07, 43, 47)
                    .unwrap()
            )
        )
    }

    #[test]
    fn monthly_job_lt() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 04, 02, 07, 45, 00)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Monthly {
                    day_of_month: 2,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 04, 01, 04, 02, 23)
                    .unwrap()
            )
        )
    }

    #[test]
    fn monthly_job_gt() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 04, 01, 07, 45, 00)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Monthly {
                    day_of_month: 1,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 16, 07, 45, 23)
                    .unwrap()
            )
        )
    }

    #[test]
    fn monthly_job_eq() {
        assert_eq!(
            FixedOffset::west_opt(OFFSET)
                .unwrap()
                .with_ymd_and_hms(2026, 04, 01, 07, 45, 00)
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Monthly {
                    day_of_month: 1,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(
                &FixedOffset::west_opt(OFFSET)
                    .unwrap()
                    .with_ymd_and_hms(2026, 03, 01, 07, 45, 00)
                    .unwrap()
            )
        )
    }
}
