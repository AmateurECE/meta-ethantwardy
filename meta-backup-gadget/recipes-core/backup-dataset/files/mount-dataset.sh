#!/bin/sh
### BEGIN INIT INFO
# Provides:          mount-dataset
# Required-Start:    $local_fs $network $syslog
# Required-Stop:     $local_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Mount the dataset volume
# Description:       Mount the dataset volume
### END INIT INFO

case "$1" in
start)
  printf "Mounting dataset\n"
  mount /dev/disk/by-label/dataset /dataset
  ;;
stop)
  printf "Unmounting dataset\n"
  umount /dataset
  ;;
restart)
  $0 stop
  $0 start
  ;;
status)
  mountpoint /dataset
  ;;
*)
  echo "Usage: $0 {start|stop|restart|status}"
  exit 1
  ;;
esac
