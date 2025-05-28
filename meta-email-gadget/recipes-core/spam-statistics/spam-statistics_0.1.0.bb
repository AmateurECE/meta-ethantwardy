LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=250dce80b2d545ccfdd59653914c3842"

SUMMARY = "Gather statistics about spam on email systems that use Rspamd"
HOMEPAGE = "https://github.com/AmateurECE/spam-statistics"

inherit cargo cargo-update-recipe-crates

SRC_URI += "git://github.com/AmateurECE/spam-statistics;protocol=https;branch=main"
SRCREV = "eaeb9d50774cea59c6602d7f5a508157f3b67d3b"
S = "${WORKDIR}/git"
PV:append = "+14"

RDEPENDS:${PN} += "gnuplot"

require ${BPN}-crates.inc

SRC_URI += "file://spam-statistics.sh"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/spam-statistics.sh -t ${D}${sysconfdir}/cron.daily
}

FILES:${PN} += "${sysconfdir}/cron.daily/spam-statistics.sh"
