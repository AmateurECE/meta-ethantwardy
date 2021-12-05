FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://iwd"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "iwd"

inherit update-rc.d

do_install:append() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/iwd ${D}${INIT_D_DIR}/${PN}
}
