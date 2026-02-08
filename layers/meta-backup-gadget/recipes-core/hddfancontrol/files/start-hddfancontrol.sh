#!/bin/sh

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

exec /usr/bin/hddfancontrol daemon -d $disks -p $hwmon/$PWM_DEVICE:200:75
