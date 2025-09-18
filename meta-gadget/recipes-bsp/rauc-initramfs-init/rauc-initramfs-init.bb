DESCRIPTION = "Find the root filesystem from the rauc slot."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "file://init.sh"

S = "${UNPACKDIR}"

do_install:append() {
    install -m755 ${UNPACKDIR}/init.sh ${D}/init

    # The kernel expects a device node for /dev/console to exist in initramfs
    # before even executing /init.
    install -d ${D}/dev
    mknod -m 622 ${D}/dev/console c 5 1
}

FILES:${PN} = "/init /dev"
