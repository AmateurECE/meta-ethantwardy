FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://${BPN}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "${PN}"

do_install:append() {
    install -d ${D}${INIT_D_DIR}
    install -m755 ${WORKDIR}/${PN} ${D}${INIT_D_DIR}/${PN}
}
