FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://main.cf.template.m4 \
"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/main.cf.template.m4 -t ${D}/etc/template/postfix

    rm -f ${D}/etc/postfix/main.cf
}

FILES:${PN}-cfg:remove = "/etc/postfix/main.cf"
FILES:${PN}-cfg:add = "/etc/template/postfix/main.cf.template.m4"
