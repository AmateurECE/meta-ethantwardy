FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://iwd.sh"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "iwd"

inherit update-rc.d

do_install:append() {
    install -Dm 0755 ${UNPACKDIR}/iwd.sh ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
}
