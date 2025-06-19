FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://main.cf \
    file://master.cf \
    file://postfix-cfg.sh \
"

INITSCRIPT_PACKAGES += "${PN}-cfg"
INITSCRIPT_NAME:${PN}-cfg = "postfix-cfg"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/main.cf ${D}/etc/postfix/main.cf.base
    install -Dm644 ${UNPACKDIR}/master.cf -t ${D}/etc/postfix

    rm -f ${D}/etc/postfix/main.cf

    install -Dm755 ${UNPACKDIR}/postfix-cfg.sh ${D}/etc/init.d/postfix-cfg
}

FILES:${PN}-cfg:remove = "/etc/postfix/main.cf"
FILES:${PN}-cfg:append = " \
    /etc/postfix/main.cf.base \
    /etc/init.d/postfix-cfg \
"
