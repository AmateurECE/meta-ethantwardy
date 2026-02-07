#!/bin/sh
### BEGIN INIT INFO
# Provides:          wireplumber
# Required-Start:    $local_fs $network $syslog pipewire
# Required-Stop:     $local_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start wireplumber session manager
# Description:       Manages PipeWire session with WirePlumber
### END INIT INFO

DAEMON=/usr/libexec/launch-wireplumber.sh
PIDFILE=/var/run/wireplumber.pid
NAME=wireplumber

. /etc/init.d/functions

case "$1" in
start)
  printf "Starting $NAME\n"
  start-stop-daemon --start --background --make-pidfile --pidfile $PIDFILE \
    --chuid pipewire --exec $DAEMON
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
  echo "Usage: $0 {start|stop|restart|status}"
  exit 1
  ;;
esac
