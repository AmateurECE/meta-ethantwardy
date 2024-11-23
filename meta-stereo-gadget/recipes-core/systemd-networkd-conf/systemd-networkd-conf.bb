LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://20-wlan0.network"
networkdir = "${sysconfdir}/systemd/network"
FILES:${PN} = "${networkdir}/20-wlan0.network"

do_install() {
    install -d ${D}${networkdir}
    install -m744 ${WORKDIR}/20-wlan0.network ${D}${networkdir}
}

