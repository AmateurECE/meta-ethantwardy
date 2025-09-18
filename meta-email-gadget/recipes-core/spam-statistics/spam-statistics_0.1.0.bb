LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=250dce80b2d545ccfdd59653914c3842"

SUMMARY = "Gather statistics about spam on email systems that use Rspamd"
HOMEPAGE = "https://github.com/AmateurECE/spam-statistics"

require ${BPN}-crates.inc

inherit cargo cargo-update-recipe-crates pkgconfig

SRC_URI += "git://github.com/AmateurECE/spam-statistics;protocol=https;branch=main;name=spam-statistics"
SRCREV_spam-statistics = "65e4700dfd8442649af6f099b732015bb2e5e98a"
SRCREV_FORMAT = "spam-statistics"
PV:append = "+31"

DEPENDS += " \
    fontconfig \
"

RDEPENDS:${PN} += " \
    ttf-roboto \
"

SRC_URI += "file://spam-statistics.sh"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/spam-statistics.sh -t ${D}${sysconfdir}/cron.daily
}

FILES:${PN} += "${sysconfdir}/cron.daily/spam-statistics.sh"
