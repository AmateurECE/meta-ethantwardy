FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:ab-mutable = " file://mutable.fstab"

do_install:append:ab-mutable() {
    install -Dm644 ${UNPACKDIR}/mutable.fstab ${D}/etc/fstab
    install -d ${D}/data
}

FILES:append:ab-mutable:${PN} = " /data"
