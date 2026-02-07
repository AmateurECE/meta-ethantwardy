FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://main.cf \
    file://aliases \
"

do_install:append() {
    rm -f ${D}/etc/postfix/main.cf
    install -Dm644 ${UNPACKDIR}/main.cf -t ${D}${sysconfdir}/postfix
    install -Dm644 ${UNPACKDIR}/aliases -t ${D}${sysconfdir}
}
