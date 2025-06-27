SUMMARY = "Basic initramfs to boot the rootfs in the rauc slot"
DESCRIPTION = "Initramfs that finds root based on rauc slot for gadgets with multiple disks"
LICENSE = "MIT"

INITRAMFS_SCRIPTS ?= "initramfs-framework-base initramfs-module-udev"

PACKAGE_INSTALL = " \
    e2fsprogs-dumpe2fs \
    e2fsprogs-tune2fs \
    rauc-initramfs-init \
    ${VIRTUAL-RUNTIME_base-utils} \
    base-passwd \
"

# Ensure the initramfs only contains the bare minimum
IMAGE_FEATURES = ""
IMAGE_LINGUAS = ""

# Don't allow the initramfs to contain a kernel, as kernel modules will depend
# on the kernel image.
PACKAGE_EXCLUDE = "kernel-image-*"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
IMAGE_NAME_SUFFIX ?= ""
IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

inherit image
