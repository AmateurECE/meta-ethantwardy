#!/bin/sh
### BEGIN INIT INFO
# Provides:          wake-controller
# Required-Start:    crond
# Required-Stop:     crond
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Controls the sleep/wake cycle of the backup server
# Description:       Go to sleep when not backing up, wake just beforehand
### END INIT INFO

set -e

PIDFILE=/run/wake-controller.pid

start() {
  echo "Starting Wake Controller..."
  start-stop-daemon \
    --start \
    --background \
    --make-pidfile --pidfile ${PIDFILE} \
    --exec /usr/bin/wake-controller
}

stop() {
  echo "Stopping Wake Controller..."
  start-stop-daemon --stop --oknodo --pidfile ${PIDFILE}
}

restart() {
  stop
  start
}

status() {
  start-stop-daemon --status --pidfile ${PIDFILE}
}

case "$1" in
start)
  start
  ;;
stop)
  stop
  ;;
restart | force-reload)
  restart
  ;;
status)
  status
  ;;
*)
  echo "Usage: $0 {start|stop|restart|force-reload|status}"
  exit 1
  ;;
esac
