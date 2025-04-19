FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://main.cf.template.m4 \
    file://master.cf \
"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/main.cf.template.m4 -t ${D}/etc/gadget
    install -Dm644 ${UNPACKDIR}/master.cf -t ${D}/etc/postfix

    rm -f ${D}/etc/postfix/main.cf
}

FILES:${PN}-cfg:remove = "/etc/postfix/main.cf"
FILES:${PN}-cfg:add = "/etc/gadget/main.cf.template.m4"
