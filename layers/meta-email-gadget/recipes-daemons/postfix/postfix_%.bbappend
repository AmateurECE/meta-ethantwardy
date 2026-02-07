FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://main.cf \
    file://master.cf \
    file://postfix-makeconf.sh \
"

PACKAGES =+ "${PN}-makeconf"

# Upstream recipe uses INITSCRIPT_NAME without a package name.
# /etc/init.d/postfix is installed in postfix-cfg package.
INITSCRIPT_NAME = ""
INITSCRIPT_PACKAGES += "${PN}-cfg ${PN}-makeconf"
INITSCRIPT_NAME:${PN}-cfg = "postfix"
INITSCRIPT_NAME:${PN}-makeconf = "postfix-makeconf"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/main.cf ${D}/etc/postfix/main.cf.base
    install -Dm644 ${UNPACKDIR}/master.cf -t ${D}/etc/postfix

    rm -f ${D}/etc/postfix/main.cf

    install -Dm755 ${UNPACKDIR}/postfix-makeconf.sh ${D}/etc/init.d/postfix-makeconf
}

FILES:${PN}-cfg:remove = "/etc/postfix/main.cf"
FILES:${PN}-cfg:append = " \
    /etc/postfix/main.cf.base \
"

FILES:${PN}-makeconf = " \
    /etc/init.d/postfix-makeconf \
"
RDEPENDS:${PN}:class-target += "${PN}-cfg ${PN}-makeconf"
