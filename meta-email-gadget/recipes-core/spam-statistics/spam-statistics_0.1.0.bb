LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=250dce80b2d545ccfdd59653914c3842"

SUMMARY = "Gather statistics about spam on email systems that use Rspamd"
HOMEPAGE = "https://github.com/AmateurECE/spam-statistics"

inherit cargo cargo-update-recipe-crates

SRC_URI += "git://github.com/AmateurECE/spam-statistics;protocol=https;branch=main"
SRCREV = "e26fd375f711e13881bcb70c7676ef7e2f9ddca4"
S = "${WORKDIR}/git"
PV:append = ".AUTOINC+5eeb394a1a"

RDEPENDS:${PN} += "gnuplot"

require ${BPN}-crates.inc

SRC_URI += "file://spam-statistics.sh"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/spam-statistics.sh -t ${D}${sysconfdir}/cron.daily
}

# TODO: Since we include AUTOINC in the version, we're likely to trigger this QA error.
INSANE_SKIP:${PN}-src = "version-going-backwards"
INSANE_SKIP:${PN}-dbg = "version-going-backwards"
INSANE_SKIP:${PN}-staticdev = "version-going-backwards"
INSANE_SKIP:${PN}-dev = "version-going-backwards"
INSANE_SKIP:${PN}-doc = "version-going-backwards"
INSANE_SKIP:${PN}-locale = "version-going-backwards"
INSANE_SKIP:${PN} = "version-going-backwards"

FILES:${PN} += "${sysconfdir}/cron.daily/spam-statistics.sh"
