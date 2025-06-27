LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SUMMARY = "Boots RAUC-based systems that have multiple disks"

RPROVIDES:${PN} += "virtual-grub-bootconf"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

SRC_URI = "file://grub.cfg"

do_install() {
    install -D grub.cfg -t ${D}/boot/grub

    # Add a symlink /grub -> /boot/grub. This sets up grubenv at the correct
    # path when this image is mounted at /boot
    ln -s boot/grub ${D}/grub
}

FILES:${PN} = " \
    /boot/grub/grub.cfg \
    /grub \
"
