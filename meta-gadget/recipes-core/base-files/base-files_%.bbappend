FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:ab-mutable = " file://mutable.fstab"

do_install:append:ab-mutable() {
    install -Dm644 ${UNPACKDIR}/mutable.fstab ${D}/etc/fstab
    install -d ${D}/data/etc
    install -d ${D}/data/var
}

FILES:append:ab-mutable:${PN} = " /data/var /data/etc"
