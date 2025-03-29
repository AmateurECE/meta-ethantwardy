#!/bin/sh

set -e
set -x

if [ ! -e email-data/empty-data.img ]; then
  printf "No file email-data/empty-data.img in the current directory\n"
  exit 1
fi

deploydir=build/tmp/deploy/images/rpi-ab-mutable-gadget
image=email-gadget-image-rpi-ab-mutable-gadget

real_image=${deploydir}/$(readlink ${deploydir}/${image}.rootfs.wic.bz2)
real_image=${real_image%%.wic.bz2}

bunzip2 -f ${real_image}.wic.bz2
losetup -P /dev/loop0 ${real_image}.wic
dd if=email-data/empty-data.img of=/dev/loop0p4
losetup -d /dev/loop0
bzip2 ${real_image}.wic
