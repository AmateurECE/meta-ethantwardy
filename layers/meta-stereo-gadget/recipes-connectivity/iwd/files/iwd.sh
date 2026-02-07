#!/bin/sh
### BEGIN INIT INFO
# Provides:          iwd
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $rmeote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start daemon at boot time
# Description:       Enable service provided by daemon.
### END INIT INFO

name=iwd
prog=/usr/libexec/$name
pid_file="/var/run/$name.pid"

RC=0

. /etc/init.d/functions

start() {
  printf "Starting iwd"
  start-stop-daemon -S -b --pidfile $pid_file -m -x $prog
  status=$?
  if [ $status != 0 ]; then
    >&2 printf '%s\n' "Couldn't start $name"
    RC=1
  fi
}

stop() {
  printf "Stopping iwd"
  start-stop-daemon -K -x $prog
}

case "$1" in
start)
  start
  ;;

stop)
  stop
  ;;

restart)
  stop
  start
  ;;

status)
  status $name
  ;;

*)
  echo "Usage: $0 {start|stop|restart|status}"
  RC=1
  ;;
esac
