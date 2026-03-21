LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SUMMARY = "Gather statistics about spam on email systems that use Rspamd"
HOMEPAGE = "https://github.com/AmateurECE/meta-ethantwardy"

require ${BPN}-crates.inc

S = "${UNPACKDIR}"
CARGO_SRC_DIR = "${EMAIL_GADGET_SRCDIR}/${BPN}"

inherit cargo cargo-update-recipe-crates pkgconfig

SRC_URI += " \
    file://${EMAIL_GADGET_SRCDIR}/${BPN} \
    file://spam-statistics.sh \
"

DEPENDS += "fontconfig"
RDEPENDS:${PN} += "ttf-roboto"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/spam-statistics.sh -t ${D}${sysconfdir}/cron.daily
}

FILES:${PN} += "${sysconfdir}/cron.daily/spam-statistics.sh"
