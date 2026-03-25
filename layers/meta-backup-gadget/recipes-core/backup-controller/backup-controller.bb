SUMMARY = "Manage backups for the dataset"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}"
SRC_URI += " \
    file://${BACKUP_GADGET_SRCDIR}/${BPN} \
    file://backup-controller.service \
    file://dataset.mount \
    file://scsi-dipm-workaround.sh \
    file://scsi-dipm-workaround.service \
    file://btrbk.conf \
    file://backup-databases.sh \
"

# Comment this line before running update_crates
require ${BPN}-crates.inc

CARGO_SRC_DIR = "${BACKUP_GADGET_SRCDIR}/backup-controller"

inherit systemd cargo cargo-update-recipe-crates

do_install:append() {
    install -Dm644 ${UNPACKDIR}/backup-controller.service -t ${D}${systemd_system_unitdir}

    install -Dm755 ${UNPACKDIR}/scsi-dipm-workaround.sh -t ${D}${bindir}
    install -Dm644 ${UNPACKDIR}/scsi-dipm-workaround.service -t ${D}${systemd_system_unitdir}
    install -Dm644 ${UNPACKDIR}/dataset.mount -t ${D}${systemd_system_unitdir}
    install -d ${D}/dataset

    # Start wg0 before postfix
    install -d ${D}${systemd_system_unitdir}/postfix.service.requires
    ln -s ../wg-quick@.service ${D}${systemd_system_unitdir}/postfix.service.requires/wg-quick@wg0.service

    install -Dm644 ${UNPACKDIR}/btrbk.conf -t ${D}${sysconfdir}/btrbk
    install -Dm755 ${UNPACKDIR}/backup-databases.sh -t ${D}/usr/libexec
}

PACKAGES =+ "${PN}-controller ${PN}-mount-dataset ${PN}-wg0"
SYSTEMD_PACKAGES = "${PN}-controller ${PN}-mount-dataset"

SYSTEMD_SERVICE:${PN}-controller = "backup-controller.service"
SYSTEMD_SERVICE:${PN}-mount-dataset = "dataset.mount"

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
    ${PN}-controller \
    ${PN}-mount-dataset \
    ${PN}-wg0 \
    ssh \
    btrbk \
"
