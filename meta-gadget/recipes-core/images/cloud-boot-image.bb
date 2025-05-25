SUMMARY = "Image for the boot partition of a Linode cloud instance"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit image

# This image only needs to contain /boot/grub/grub.cfg
PACKAGE_INSTALL = " \
    rauc-grub-bootconf \
"
