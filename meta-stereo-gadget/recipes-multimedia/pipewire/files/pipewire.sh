#!/bin/sh
### BEGIN INIT INFO
# Provides:          pipewire
# Required-Start:    $local_fs $network $syslog
# Required-Stop:     $local_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start pipewire daemon
# Description:       Starts the PipeWire multimedia daemon
### END INIT INFO

DAEMON=/usr/libexec/launch-pipewire.sh
PIDFILE=/var/run/pipewire.pid
NAME=pipewire
PIPEWIRE_RUNTIME_DIR=/run/pipewire

. /etc/init.d/functions

mkdir -p $PIPEWIRE_RUNTIME_DIR
chown pipewire:pipewire $PIPEWIRE_RUNTIME_DIR

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
