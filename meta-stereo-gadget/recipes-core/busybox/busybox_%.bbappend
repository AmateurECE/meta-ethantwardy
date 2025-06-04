FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://stereo.cfg \
    file://ntpd.sh \
"

# TODO: Should this be moved into a common place?
PACKAGES =+ "${PN}-ntpd"
INITSCRIPT_PACKAGES += "${PN}-ntpd"
INITSCRIPT_NAME:${PN}-ntpd = "ntpd"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/ntpd.sh ${D}/etc/init.d/ntpd
}

FILES:${PN}-ntpd = "/etc/init.d/ntpd"
