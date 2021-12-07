###############################################################################
# NAME:             bluez-iot-agent_git.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Yocto recipe for the bluez-iot-agent project
#
# CREATED:          11/06/2021
#
# LAST EDITED:      12/06/2021
###

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = " \
git://github.com/AmateurECE/bluez-iot-agent;protocol=https;branch=trunk \
file://${PN} \
"

PV = "1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEPENDS:append = " libhandlebars glib-2.0 libsoup-3.0 glib-2.0-native"
RDEPENDS:${PN} += "libhandlebars glib-2.0"

FILES:${PN} += "\
${datadir}/dbus-1/system.d/${PN}.conf \
${datadir}/${PN}/index.html.hbs \
${datadir}/${PN}/style.css \
"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "${PN}"

inherit meson pkgconfig update-rc.d

do_install:append() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/${PN} ${D}${INIT_D_DIR}/${PN}
}

###############################################################################
