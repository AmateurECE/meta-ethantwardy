#!/bin/sh

mount_overlay() {
  mountpoint=$1
  lower=$2
  upper=$mountpoint/.upper
  work=$mountpoint/.work

  mkdir -p $upper
  mkdir -p $lower

  if ! mount -n -t overlay \
    -o upperdir=$upper \
    -o lowerdir=$lower \
    -o workdir=$work \
    -o index=off,xino=off,redirect_dir=off,metacopy=off \
    $upper $lower; then
    printf "<INITRAMFS>: Mounting overlay on $lower failed!\n"
    exec sh
  fi
}

# Minimal setup
mkdir -p /proc /sys /run/lock /var/lock
mount -t proc proc /proc
mount -t sysfs sysfs /sys
mount -t devtmpfs devtmpfs /dev

# Extract rauc.slot from /proc/cmdline
SLOT=$(cat /proc/cmdline | sed -n 's/.*rauc\.slot=\([^ ]*\).*/\1/p')

# Fallback if not found
if [ -z "$SLOT" ]; then
  printf "rauc.slot not specified, dropping into root shell\n"
  exec sh
fi

printf "Booting from slot: $SLOT (LABEL=$SLOT)\n"

# Mount root
NEWROOT=/newroot
mkdir $NEWROOT

ROOT_MOUNTED=0
TRIES=0

# Retry because the kernel may not have finished parsing labels by the time
# control gets here.
while [ $TRIES -lt 3 ] && [ $ROOT_MOUNTED = "0" ]; do
  if ! mount -o ro LABEL="$SLOT" $NEWROOT; then
    TRIES=$(($TRIES + 1))
    sleep 1
  else
    ROOT_MOUNTED=1
  fi
done

if [ $ROOT_MOUNTED = "0" ]; then
  printf "Failed to mount rootfs with label '$SLOT'\n"
  exec sh
fi

printf "Mounted rootfs from label '$SLOT'\n"

mount -t proc proc $NEWROOT/proc
mount -t sysfs sysfs $NEWROOT/sys
mount -t devtmpfs devtmpfs $NEWROOT/dev

# Mount data partition and overlays.
if mount -n -t btrfs -o discard=async,space_cache=v2,subvolid=5 \
  LABEL=gadget-data $NEWROOT/data; then
  mount_overlay "$NEWROOT/data/@etc" $NEWROOT/etc
  mount_overlay "$NEWROOT/data/@var" $NEWROOT/var
else
  printf "<INITRAMFS>: Mounting /data failed!\n"
  exec sh
fi

# Mount /home. See mutable.fstab in the metadata sources for more information.
mount -n -t btrfs -o discard=async,space_cache=v2,subvol=/@home \
  LABEL=gadget-data $NEWROOT/home

if [ ! -d $NEWROOT/home/root ]; then
  mkdir $NEWROOT/home/root
fi

[ -z "$CONSOLE" ] && CONSOLE="/dev/console"

# Switch to real root
exec switch_root -c $CONSOLE $NEWROOT /sbin/init "$@"
