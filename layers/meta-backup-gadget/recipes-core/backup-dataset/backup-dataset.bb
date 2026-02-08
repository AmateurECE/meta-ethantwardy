SUMMARY = "Manage backups for the dataset"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}"
SRC_URI += " \
    file://wake-controller.sh \
    file://wake-controller.service \
    file://dataset.mount \
    file://scsi-dipm-workaround.sh \
    file://scsi-dipm-workaround.service \
    file://btrbk.conf \
    file://btrbk.sh \
    file://backup-databases.sh \
"

do_install() {
    install -Dm755 ${UNPACKDIR}/wake-controller.sh -t ${D}${bindir}
    install -Dm644 ${UNPACKDIR}/wake-controller.service -t ${D}${systemd_system_unitdir}

    install -Dm755 ${UNPACKDIR}/scsi-dipm-workaround.sh -t ${D}${bindir}
    install -Dm644 ${UNPACKDIR}/scsi-dipm-workaround.service -t ${D}${systemd_system_unitdir}
    install -Dm644 ${UNPACKDIR}/dataset.mount -t ${D}${systemd_system_unitdir}
    install -d ${D}/dataset

    # Start wg0 before postfix
    install -d ${D}${systemd_system_unitdir}/postfix.service.requires
    ln -s ../wg-quick@.service ${D}${systemd_system_unitdir}/postfix.service.requires/wg-quick@wg0.service

    install -Dm644 ${UNPACKDIR}/btrbk.conf -t ${D}${sysconfdir}/btrbk
    install -Dm755 ${UNPACKDIR}/btrbk.sh -t ${D}${sysconfdir}/cron.daily
    install -Dm755 ${UNPACKDIR}/backup-databases.sh -t ${D}${sysconfdir}/cron.weekly
}

inherit systemd

PACKAGES =+ "${PN}-wake-controller ${PN}-mount-dataset ${PN}-wg0"
SYSTEMD_PACKAGES = "${PN}-wake-controller ${PN}-mount-dataset"

SYSTEMD_SERVICE:${PN}-wake-controller = "wake-controller.service"
SYSTEMD_SERVICE:${PN}-mount-dataset = "dataset.mount"

FILES:${PN}-wake-controller += " \
    ${systemd_system_unitdir}/wake-controller.service \
    ${bindir}/wake-controller \
"
FILES:${PN}-mount-dataset += " \
    ${systemd_system_unitdir}/scsi-dipm-workaround.service \
    /dataset \
"
FILES:${PN}-wg0 += " \
    ${systemd_system_unitdir}/postfix.service.requires/wg-quick@wg0.service \
"

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
