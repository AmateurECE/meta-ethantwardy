FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://main.cf \
    file://master.cf \
    file://postfix-makeconf.sh \
    file://postfix-makeconf.service \
"

PACKAGES =+ "${PN}-makeconf"

SYSTEMD_PACKAGES += "${PN}-makeconf"
SYSTEMD_SERVICE:${PN}-makeconf = "postfix-makeconf.service"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/main.cf ${D}/etc/postfix/main.cf.base
    install -Dm644 ${UNPACKDIR}/master.cf -t ${D}/etc/postfix

    rm -f ${D}/etc/postfix/main.cf

    install -Dm755 ${UNPACKDIR}/postfix-makeconf.sh -t ${D}${bindir}
    install -Dm644 ${UNPACKDIR}/postfix-makeconf.service -t ${D}${systemd_system_unitdir}
}

FILES:${PN}-makeconf = " \
    ${systemd_system_unitdir}/postfix-makeconf.service \
    ${systemd_system_unitdir}/postfix.service.requires/postfix-makeconf.service \
"
RDEPENDS:${PN}:class-target += "${PN}-makeconf"
