###############################################################################
# NAME:             libhandlebars_${PV}.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Recipe for libhandlebars
#
# CREATED:          11/28/2021
#
# LAST EDITED:      11/29/2021
###

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97e4de027b3766fc2928be5a56f7aacf"

SRC_URI="git://github.com/AmateurECE/libhandlebars;protocol=https;branch=trunk"
SRCREV = "v${PV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEPENDS:append:class-target = " peg-native"
RPROVIDES:${PN} += "libhandlebars"

inherit meson pkgconfig

FILES:${PN}:append = " \
${libdir} \
${libdir}/${PN}.so \
${libdir}/${PN}.so.0 \
${libdir}/${PN}.so.${PV} \
"

do_install:append() {
        install -d ${D}${libdir}
        install -m0755 ${B}/${PN}.so ${D}${libdir}/${PN}.so.${PV}
        ln -sr ${D}${libdir}/${PN}.so.${PV} ${D}${libdir}/${PN}.so.0
        rm -f ${D}${libdir}/${PN}.so
        ln -sr ${D}${libdir}/${PN}.so.0 ${D}${libdir}/${PN}.so
}

###############################################################################
