#!/bin/sh
### BEGIN INIT INFO
# Provides:          dovecot
# Required-Start:    $local_fs $remote_fs $network $syslog
# Required-Stop:     $local_fs $remote_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Dovecot IMAP/POP3 server
# Description:       Starts the Dovecot mail server daemon.
### END INIT INFO

DAEMON=/usr/sbin/dovecot
NAME=dovecot
DESC="Dovecot IMAP/POP3 server"
PIDFILE=/var/run/dovecot/master.pid

case "$1" in
  start)
    echo "Starting $DESC..."
    start-stop-daemon --start --quiet --exec $DAEMON --background
    echo "$NAME started."
    ;;
  stop)
    echo "Stopping $DESC..."
    start-stop-daemon --stop --quiet --pidfile $PIDFILE
    echo "$NAME stopped."
    ;;
  restart)
    $0 stop
    sleep 2
    $0 start
    ;;
  status)
    if [ -f "$PIDFILE" ] && kill -0 $(cat "$PIDFILE") 2>/dev/null; then
      echo "$NAME is running with PID $(cat "$PIDFILE")."
    else
      echo "$NAME is not running."
    fi
    ;;
  *)
    echo "Usage: $0 {start|stop|restart|status}"
    exit 1
    ;;
esac

exit 0
