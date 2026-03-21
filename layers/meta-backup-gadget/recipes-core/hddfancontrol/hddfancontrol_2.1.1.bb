LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SUMMARY = "Daemon to regulate fan speed according to hard drive temperature on \
Linux"
HOMEPAGE = "https://crates.io/crates/hddfancontrol"

inherit systemd cargo cargo-update-recipe-crates

SRC_URI += " \
    git://github.com/desbma/hddfancontrol;protocol=https;branch=master;tag=${PV} \
    file://hddfancontrol.service \
    file://start-hddfancontrol.sh \
"

RDEPENDS:${PN} += " \
    hdparm \
    smartmontools \
    backup-dataset \
"

SYSTEMD_SERVICE:${PN} = "${PN}.service"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/${PN}.service -t ${D}${systemd_system_unitdir}
    install -Dm755 ${UNPACKDIR}/start-${PN}.sh -t ${D}${bindir}
}

FILES:${PN} += "${systemd_system_unitdir}/${PN}.service"

require ${BPN}-crates.inc
