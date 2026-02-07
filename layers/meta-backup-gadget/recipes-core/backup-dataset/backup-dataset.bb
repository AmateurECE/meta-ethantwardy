SUMMARY = "Manage backups for the dataset"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}"
SRC_URI += " \
    file://wake-controller.sh \
    file://run-wake-controller.sh \
    file://mount-dataset.sh \
    file://btrbk.conf \
    file://btrbk.sh \
    file://wg0.sh \
    file://backup-databases.sh \
"

do_install() {
    install -Dm755 ${UNPACKDIR}/wake-controller.sh ${D}${bindir}/wake-controller
    install -Dm755 ${UNPACKDIR}/run-wake-controller.sh ${D}${sysconfdir}/init.d/wake-controller
    install -Dm755 ${UNPACKDIR}/mount-dataset.sh ${D}${sysconfdir}/init.d/mount-dataset
    install -Dm755 ${UNPACKDIR}/wg0.sh ${D}${sysconfdir}/init.d/wg0
    install -Dm644 ${UNPACKDIR}/btrbk.conf -t ${D}${sysconfdir}/btrbk
    install -d ${D}/dataset

    install -Dm755 ${UNPACKDIR}/btrbk.sh -t ${D}${sysconfdir}/cron.daily
    install -Dm755 ${UNPACKDIR}/backup-databases.sh -t ${D}${sysconfdir}/cron.weekly
}

inherit update-rc.d

PACKAGES =+ "${PN}-wake-controller ${PN}-mount-dataset ${PN}-wg0"
INITSCRIPT_PACKAGES = "${PN}-wake-controller ${PN}-mount-dataset ${PN}-wg0"

INITSCRIPT_NAME:${PN}-wake-controller = "wake-controller"
INITSCRIPT_PARAMS:${PN}-wake-controller = "start 5 3 4 5 . stop 30 0 1 6 ."

INITSCRIPT_NAME:${PN}-mount-dataset = "mount-dataset"
INITSCRIPT_PARAMS:${PN}-mount-dataset = "start 5 3 4 5 . stop 30 0 1 6 ."

INITSCRIPT_NAME:${PN}-wg0 = "wg0"
INITSCRIPT_PARAMS:${PN}-wg0 = "start 5 3 4 5 . stop 30 0 1 6 ."

FILES:${PN}-wake-controller += " \
    ${sysconfdir}/init.d/wake-controller \
    ${bindir}/wake-controller \
"
FILES:${PN}-mount-dataset += " \
    ${sysconfdir}/init.d/mount-dataset \
    /dataset \
"
FILES:${PN}-wg0 += "${sysconfdir}/init.d/wg0"

RDEPENDS:${PN}-wake-controller += "util-linux"
RDEPENDS:${PN}-wg0 += "wireguard-tools"
RDEPENDS:${PN} += " \
    ${PN}-wake-controller \
    ${PN}-mount-dataset \
    ${PN}-wg0 \
    ssh \
    btrbk \
    cronie \
"
