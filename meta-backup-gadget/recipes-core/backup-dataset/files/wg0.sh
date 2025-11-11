#!/bin/sh
### BEGIN INIT INFO
# Provides:          wg0
# Required-Start:    $network
# Required-Stop:     $network
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: WireGuard wg0 VPN interface
# Description:       Start/stop the wg0 interface using wg-quick
### END INIT INFO

INTERFACE="wg0"

start() {
  echo "Starting WireGuard interface $INTERFACE..."
  wg-quick up $INTERFACE
  RETVAL=$?
  if [ $RETVAL -eq 0 ]; then
    echo "WireGuard $INTERFACE started successfully."
  else
    echo "Failed to start WireGuard $INTERFACE."
  fi
  return $RETVAL
}

stop() {
  echo "Stopping WireGuard interface $INTERFACE..."
  wg-quick down $INTERFACE
  RETVAL=$?
  if [ $RETVAL -eq 0 ]; then
    echo "WireGuard $INTERFACE stopped successfully."
  else
    echo "Failed to stop WireGuard $INTERFACE."
  fi
  return $RETVAL
}

restart() {
  stop
  start
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
  wg show $INTERFACE
  ;;
*)
  echo "Usage: $0 {start|stop|restart|force-reload|status}"
  exit 1
  ;;
esac
