#!/bin/sh

# Minimal setup
mkdir -p /proc /sys /run/lock /var/lock
mount -t proc proc /proc
mount -t sysfs sysfs /sys
mount -t devtmpfs devtmpfs /dev

# Extract rauc.slot from /proc/cmdline
SLOT=$(cat /proc/cmdline | sed -n 's/.*rauc\.slot=\([^ ]*\).*/\1/p')

# Fallback if not found
if [ -z "$SLOT" ]; then
  printf "rauc.slot not specified, dropping into root shell"
  exec sh
fi

echo "Booting from slot: $SLOT (LABEL=$SLOT)"

# Mount root
NEWROOT=/newroot
mkdir $NEWROOT

ROOT_MOUNTED=0
TRIES=0

while [ $TRIES -lt 3 ] && [ $ROOT_MOUNTED = "0" ]; do
  if ! mount -o ro LABEL="$SLOT" $NEWROOT; then
    TRIES=$(($TRIES + 1))
    sleep 1
  else
    ROOT_MOUNTED=1
  fi
done

if [ $ROOT_MOUNTED = "0" ]; then
  echo "Failed to mount rootfs with label '$SLOT'"
  exec sh
fi

echo "Mounted rootfs from label '$SLOT'"

mount -t proc proc $NEWROOT/proc
mount -t sysfs sysfs $NEWROOT/sys
mount -t devtmpfs devtmpfs $NEWROOT/dev

[ -z "$CONSOLE" ] && CONSOLE="/dev/console"

# Switch to real root
exec switch_root -c $CONSOLE $NEWROOT /sbin/preinit "$@"
