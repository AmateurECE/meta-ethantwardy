#!/bin/sh

# Sleep to give the kernel time to reload the partition table on slow disks
sleep 1
eval TARGET_LABEL=\$RAUC_SLOT_BOOTNAME_${RAUC_TARGET_SLOTS}
e2label /dev/disk/by-label/rootfs-bundle ${TARGET_LABEL}
