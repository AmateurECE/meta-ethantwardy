LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/sources-unpack"

SRC_URI += "file://mount-overlay.sh"

do_install() {
    install -Dm755 ${S}/mount-overlay.sh ${D}/usr/bin/mount-overlay
}

FILES:${PN} = "/usr/bin/mount-overlay"
