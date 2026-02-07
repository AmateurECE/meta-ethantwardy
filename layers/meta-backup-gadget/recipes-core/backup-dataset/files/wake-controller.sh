#!/bin/sh

disable_wakeup_sources() {
  echo XHC0 >/proc/acpi/wakeup
}

# Blocks until there are no running cron jobs
await_cron_jobs() {
  running=0
  start_regex='cron\.info.*CMD\W'
  end_regex='cron\.info.*CMDEND\W'
  tail -f /var/log/messages | while read LOGLINE; do
    if [[ "${LOGLINE}" =~ $start_regex ]]; then
      running=1
    elif [[ "${LOGLINE}" =~ $end_regex && "$running" != "0" ]]; then
      break
    fi
  done

  # Grace period for crond to complete any post trigger actions, like send an
  # email.
  sleep 5
}

# Puts the system to sleep until a few seconds before the next cron job is
# scheduled to run.
sleep_until_next_cron_job() {
  waketime=$(($(cronnext | awk '{print $2}') - 30))
  logger "Waking up at $(date -d @${waketime})"

  if [ -f /etc/caffeinated ]; then
    logger "Not putting system to sleep because /etc/caffeinated exists"
    dryrun=-n
  else
    dryrun=""
  fi

  rtcwake ${dryrun} -m mem -t ${waketime}
  # rtcwake does not block until the waketime if we specify a dry-run, so put
  # the service to sleep instead.
  if [ -n ${dryrun} ]; then
    sleep $((${waketime} - $(date +%s)))
  fi
}

# Stay awake for the first 60 seconds, at least. This gives the user time to
# log in and create /etc/caffeinated, if they so desire.
sleep 60

disable_wakeup_sources

while true; do
  sleep_until_next_cron_job
  await_cron_jobs
done
