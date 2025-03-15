FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://dovecot.conf \
    file://tls-dovecot.conf \
"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/dovecot.conf -t ${D}/etc/dovecot
    install -Dm644 ${UNPACKDIR}/tls-dovecot.conf -t ${D}/etc/dovecot
}

FILES:${PN} += " \
    /etc/dovecot/dovecot.conf \
    /etc/dovecot/tls-dovecot.conf \
"
