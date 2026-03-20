use std::process::Command;

use chrono::{DateTime, Datelike as _, Local, NaiveTime, TimeDelta, Weekday};

pub enum Schedule {
    Daily(NaiveTime),
    Weekly { weekday: Weekday, time: NaiveTime },
}

pub struct Job {
    pub command: Box<dyn Fn() -> Command>,
    pub schedule: Schedule,
}

impl Job {
    pub fn next_invocation(&self, after: &DateTime<Local>) -> DateTime<Local> {
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
    use super::*;

    #[test]
    fn daily_job_lt() {
        assert_eq!(
            "2026-03-15T04:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Daily("04:45:00".parse().unwrap()),
            }
            .next_invocation(&"2026-03-15T02:07:47-05:00".parse().unwrap(),)
        )
    }

    #[test]
    fn daily_job_gt() {
        assert_eq!(
            "2026-03-16T04:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Daily("04:45:00".parse().unwrap()),
            }
            .next_invocation(&"2026-03-15T04:45:07-05:00".parse().unwrap(),)
        )
    }

    #[test]
    fn daily_job_eq() {
        assert_eq!(
            "2026-03-16T04:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Daily("04:45:00".parse().unwrap()),
            }
            .next_invocation(&"2026-03-15T04:45:00-05:00".parse().unwrap(),)
        )
    }

    #[test]
    fn weekly_job_lt_sameday() {
        assert_eq!(
            "2026-03-22T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("/usr/libexec/backup-databases.sh")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Sun,
                    time: "07:45:00".parse().unwrap(),
                }
            }
            .next_invocation(&"2026-03-16T07:43:47-05:00".parse().unwrap(),)
        )
    }

    #[test]
    fn weekly_job_lt() {
        assert_eq!(
            "2026-03-16T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Mon,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(&"2026-03-15T04:02:23-05:00".parse().unwrap(),)
        )
    }

    #[test]
    fn weekly_job_gt() {
        assert_eq!(
            "2026-03-23T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Mon,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(&"2026-03-16T07:45:23-05:00".parse().unwrap(),)
        )
    }

    #[test]
    fn weekly_job_eq() {
        assert_eq!(
            "2026-03-23T07:45:00-05:00"
                .parse::<DateTime<Local>>()
                .unwrap(),
            Job {
                command: Box::new(|| Command::new("ls")),
                schedule: Schedule::Weekly {
                    weekday: Weekday::Mon,
                    time: "07:45:00".parse().unwrap()
                },
            }
            .next_invocation(&"2026-03-16T07:45:00-05:00".parse().unwrap(),)
        )
    }
}
