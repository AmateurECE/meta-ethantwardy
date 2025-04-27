FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://email.cfg \
    file://ntpd.sh \
"

PACKAGES =+ "${PN}-ntpd"
INITSCRIPT_PACKAGES += "${PN}-ntpd"
INITSCRIPT_NAME:${PN}-ntpd = "ntpd"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/ntpd.sh ${D}/etc/init.d/ntpd
}

FILES:${PN}-ntpd = "/etc/init.d/ntpd"
