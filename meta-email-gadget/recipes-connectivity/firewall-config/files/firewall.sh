#!/bin/sh
### BEGIN INIT INFO
# Provides:          firewall
# Required-Start:    $network
# Required-Stop:     $network
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Load nftables firewall ruleset
# Description:       Loads nftables ruleset at start, flushes on stop
### END INIT INFO

RULESET="/etc/nftables/nftables.conf"

start() {
  echo "Starting nftables firewall..."
  /usr/sbin/nft -f "$RULESET"
  echo "nftables rules loaded."
}

stop() {
  echo "Stopping nftables firewall..."
  /usr/sbin/nft flush ruleset
  echo "nftables rules flushed."
}

case "$1" in
start)
  start
  ;;
stop)
  stop
  ;;
restart | reload | force-reload)
  stop
  start
  ;;
status)
  /usr/sbin/nft list ruleset
  ;;
*)
  echo "Usage: $0 {start|stop|restart|reload|force-reload|status}"
  exit 1
  ;;
esac
