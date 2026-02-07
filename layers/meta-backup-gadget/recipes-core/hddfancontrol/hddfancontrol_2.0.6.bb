LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SUMMARY = "Daemon to regulate fan speed according to hard drive temperature on Linux"
HOMEPAGE = "https://crates.io/crates/hddfancontrol"

inherit update-rc.d cargo cargo-update-recipe-crates

SRC_URI += " \
    git://github.com/desbma/hddfancontrol;protocol=https;branch=master;tag=${PV} \
    file://hddfancontrol.sh \
"

RDEPENDS:${PN} += " \
    hdparm \
    smartmontools \
    backup-dataset \
"

INITSCRIPT_NAME = "${PN}"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/hddfancontrol.sh ${D}${sysconfdir}/init.d/${PN}
}

FILES:${PN} += " \
    ${sysconfdir}/init.d/${PN} \
"

require ${BPN}-crates.inc
