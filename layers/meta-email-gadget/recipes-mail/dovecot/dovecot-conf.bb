SUMMARY = "Dovecot configuration for the email gadget"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}"

SRC_URI = " \
    file://dovecot.conf \
    file://tls-dovecot.conf \
    file://rspamd-learn-spam.sh \
    file://rspamd-learn-ham.sh \
    file://learn-spam.sieve \
    file://learn-ham.sieve \
    file://spam.sieve \
"

RDEPENDS:${PN} += "postfix"

do_install () {
    install -Dm644 ${UNPACKDIR}/dovecot.conf -t ${D}/etc/dovecot
    install -Dm644 ${UNPACKDIR}/tls-dovecot.conf -t ${D}/etc/dovecot

    install -Dm755 ${UNPACKDIR}/rspamd-learn-spam.sh -t ${D}/etc/dovecot/bin
    install -Dm755 ${UNPACKDIR}/rspamd-learn-ham.sh -t ${D}/etc/dovecot/bin
    install -Dm644 ${UNPACKDIR}/learn-spam.sieve -t ${D}/etc/dovecot/sieve
    install -Dm644 ${UNPACKDIR}/learn-ham.sieve -t ${D}/etc/dovecot/sieve
    install -Dm644 ${UNPACKDIR}/spam.sieve -t ${D}/etc/dovecot/sieve
}

pkg_postinst:${PN}() {
    chown vmail:vmail $D/etc/dovecot/sieve
}

FILES:${PN} += " \
    /etc/dovecot/dovecot.conf \
    /etc/dovecot/tls-dovecot.conf \
    /etc/dovecot/bin/* \
    /etc/dovecot/sieve/* \
"
