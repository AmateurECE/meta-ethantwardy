FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://named.conf.options"

do_install:append() {
    install -m644 ${UNPACKDIR}/named.conf.options -t ${D}/etc/bind
}
