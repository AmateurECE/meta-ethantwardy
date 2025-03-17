FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://dovecot.conf \
    file://tls-dovecot.conf \
    file://dovecot.sh \
"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/dovecot.conf -t ${D}/etc/dovecot
    install -Dm644 ${UNPACKDIR}/tls-dovecot.conf -t ${D}/etc/dovecot

    install -Dm755 ${UNPACKDIR}/dovecot.sh ${D}${sysconfdir}/init.d/${PN}
}

FILES:${PN} += " \
    /etc/dovecot/dovecot.conf \
    /etc/dovecot/tls-dovecot.conf \
"

inherit update-rc.d

INITSCRIPT_NAME = "dovecot"
