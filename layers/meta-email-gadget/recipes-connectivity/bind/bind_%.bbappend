FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://named.conf.options \
    file://tmpfiles.conf \
"

do_install:append() {
    install -m644 ${UNPACKDIR}/named.conf.options -t ${D}/etc/bind
    install -Dm644 ${UNPACKDIR}/tmpfiles.conf ${D}/usr/lib/tmpfiles.d/named.conf

    echo 'nameserver 127.0.0.1' > ${D}/etc/resolv.conf
}

FILES:${PN} += " \
    /etc/resolv.conf \
    /usr/lib/tmpfiles.d/named.conf \
"
