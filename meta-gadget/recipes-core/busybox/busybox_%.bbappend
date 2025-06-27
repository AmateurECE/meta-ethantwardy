FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "${@bb.utils.contains('PACKAGECONFIG', 'ntpd', '', 'file://ntpd.cfg', d)}"
SRC_URI += "file://ntpd.sh"

PACKAGES =+ "${PN}-ntpd"
INITSCRIPT_PACKAGES += "${PN}-ntpd"
INITSCRIPT_NAME:${PN}-ntpd = "ntpd"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/ntpd.sh ${D}/etc/init.d/ntpd
}

FILES:${PN}-ntpd = "/etc/init.d/ntpd"

RDEPENDS:${PN} += "${@bb.utils.contains('PACKAGECONFIG', 'ntpd', '', 'busybox-ntpd', d)}"
