FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://mutable.fstab"

do_install:append() {
    if [ -n "${MUTABLE_GADGET}" ]; then
        install -Dm644 ${UNPACKDIR}/mutable.fstab ${D}/etc/fstab
    fi

    install -d ${D}/data/etc
    install -d ${D}/data/var
}

FILES:${PN} += "/data/var /data/etc"
