#!/bin/sh
### BEGIN INIT INFO
# Provides:          ntpd
# Required-Start:    $network
# Required-Stop:
# Default-Start:     2 3 4 5
# Default-Stop:
# Short-Description: Start ntpd in client mode
### END INIT INFO

NTPD_BIN=/usr/sbin/ntpd
NTPD_OPTS="-p pool.ntp.org -g"

PIDFILE=/var/run/ntpd.pid

start() {
  echo "Starting ntpd..."
  start-stop-daemon --start --quiet --exec $NTPD_BIN -- $NTPD_OPTS
}

stop() {
  echo "Stopping ntpd..."
  start-stop-daemon --stop --quiet --exec $NTPD_BIN
}

restart() {
  stop
  sleep 1
  start
}

case "$1" in
start)
  start
  ;;
stop)
  stop
  ;;
restart | reload | force-reload)
  restart
  ;;
status)
  pidof ntpd >/dev/null && echo "ntpd is running." || echo "ntpd is not running."
  ;;
*)
  echo "Usage: $0 {start|stop|restart|status}"
  exit 1
  ;;
esac

exit 0
