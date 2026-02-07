#!/bin/sh
### BEGIN INIT INFO
# Provides:          hddfancontrol
# Required-Start:    $local_fs $network $syslog mount-dataset
# Required-Stop:     $local_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start hddfancontrol
# Description:       Regulate HDD temperature
### END INIT INFO

DAEMON=/usr/bin/hddfancontrol
PIDFILE=/var/run/hddfancontrol.pid
NAME=hddfancontrol

. /etc/init.d/functions

FAN_CONTROLLER=nct6792
PWM_DEVICE=pwm3

# AWK script that parses the output from btrfs filesystem show to identify the
# devices that make up this volume.
read -r -d '' SCRIPT <<'EOF'
/Total devices/{
    header = 1
    next
}

$1 ~ "devid" && header == 1 {
    devices = devices " " $NF
}

END { print devices }
EOF

# Wait until the mountpoint is ready...
while ! mountpoint /dataset >/dev/null; do
  sleep 1
done

disks=$(btrfs filesystem show /dataset | awk "$SCRIPT")

# Find the hwmon device that corresponds to the fan controller.
hwmon=$(for f in /sys/class/hwmon/hwmon*; do
  if grep -q $FAN_CONTROLLER $f/name; then
    echo $f
  fi
done)

case "$1" in
start)
  printf "Starting $NAME\n"
  start-stop-daemon --start --background --make-pidfile --pidfile $PIDFILE \
    --exec $DAEMON -- \
    daemon -d $disks -p $hwmon/$PWM_DEVICE:200:75
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
