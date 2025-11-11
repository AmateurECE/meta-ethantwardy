SUMMARY = "Manage backups for the dataset"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}"
SRC_URI += " \
    file://mount-dataset.sh \
    file://btrbk.conf \
    file://btrbk.sh \
    file://wg0.sh \
"

do_install() {
    install -Dm755 ${UNPACKDIR}/mount-dataset.sh ${D}${sysconfdir}/init.d/mount-dataset
    install -Dm755 ${UNPACKDIR}/wg0.sh ${D}${sysconfdir}/init.d/wg0
    install -Dm755 ${UNPACKDIR}/btrbk.sh -t ${D}${sysconfdir}/cron.daily
    install -Dm644 ${UNPACKDIR}/btrbk.conf -t ${D}${sysconfdir}/btrbk
    install -d ${D}/dataset
}

inherit update-rc.d

PACKAGES =+ "${PN}-mount-dataset ${PN}-wg0"
INITSCRIPT_PACKAGES = "${PN}-mount-dataset ${PN}-wg0"

INITSCRIPT_NAME:${PN}-mount-dataset = "mount-dataset"
INITSCRIPT_PARAMS:${PN}-mount-dataset = "start 5 3 4 5 . stop 30 0 1 6 ."

INITSCRIPT_NAME:${PN}-wg0 = "wg0"
INITSCRIPT_PARAMS:${PN}-wg0 = "start 5 3 4 5 . stop 30 0 1 6 ."

FILES:${PN}-mount-dataset += " \
    ${sysconfdir}/init.d/mount-dataset \
    /dataset \
"
FILES:${PN}-wg0 += "${sysconfdir}/init.d/wg0"

DEPENDS += "wireguard-tools"
RDEPENDS:${PN} += "${PN}-mount-dataset ${PN}-wg0"
