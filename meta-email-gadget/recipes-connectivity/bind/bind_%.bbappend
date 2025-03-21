FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://named.conf.options"

do_install:append() {
    install -m644 ${UNPACKDIR}/named.conf.options -t ${D}/etc/bind

    echo 'nameserver 127.0.0.1' > ${D}/etc/resolv.conf
}

FILES:${PN} += " /etc/resolv.conf"
