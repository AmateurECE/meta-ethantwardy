#!/bin/sh
### BEGIN INIT INFO
# Provides:          fail2ban
# Required-Start:    $network $remote_fs $syslog
# Required-Stop:     $network $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start Fail2Ban service
# Description:       Ban hosts that cause multiple authentication errors.
### END INIT INFO

NAME=fail2ban
DAEMON=/usr/bin/fail2ban-server
PIDFILE=/var/run/fail2ban/fail2ban.pid

. /etc/init.d/functions

case "$1" in
start)
  printf "Starting $NAME\n"
  start-stop-daemon --start --background --pidfile $PIDFILE --exec $DAEMON
  ;;
stop)
  printf "Stopping $NAME\n"
  start-stop-daemon --stop --pidfile $PIDFILE
  ;;
restart)
  $0 stop
  sleep 1
  $0 start
  ;;
status)
  status $NAME
  ;;
*)
  echo "Usage: $0 {start|stop|restart|status}" >&2
  exit 3
  ;;
esac
