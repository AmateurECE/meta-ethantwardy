#!/bin/sh
### BEGIN INIT INFO
# Provides:          rspamd
# Required-Start:    $network $local_fs $remote_fs
# Required-Stop:     $network $local_fs $remote_fs
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Rspamd spam filtering system
# Description:       Starts and stops the Rspamd spam filtering daemon
### END INIT INFO

DAEMON=/usr/bin/rspamd
DAEMON_USER=rspamd:rspamd
PIDFILE=/var/run/rspamd.pid
NAME=rspamd
DESC="Rspamd spam filtering daemon"

start() {
  printf '%s' "Starting rspamd..."
  if [ -f $PIDFILE ]; then
    echo "already running"
    return 0
  fi
  start-stop-daemon --start --quiet --background --pidfile $PIDFILE \
    --make-pidfile --chuid $DAEMON_USER --exec $DAEMON -- -f
  echo "OK"
}

stop() {
  echo "Stopping rspamd..."
  start-stop-daemon --stop --quiet --pidfile $PIDFILE
  RETVAL=$?
  [ -f $PIDFILE ] && rm -f $PIDFILE
}

restart() {
  stop
  sleep 1
  start
}

status() {
  if [ -f $PIDFILE ] && [ -d /proc/$(cat $PIDFILE) ]; then
    echo "OK"
  else
    echo "Stopped"
  fi
}

case "$1" in
start) start ;;
stop) stop ;;
restart | force-reload) restart ;;
status) status ;;
*) echo "Usage: $0 {start|stop|restart|status}" ;;
esac

exit 0
