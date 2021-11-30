###############################################################################
# NAME:             bluez-iot-agent_git.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Yocto recipe for the bluez-iot-agent project
#
# CREATED:          11/06/2021
#
# LAST EDITED:      11/29/2021
###

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = "git://github.com/AmateurECE/bluez-iot-agent;protocol=https;branch=trunk"

PV = "1.0+git${SRCPV}"
SRCREV = "4a6995eaba32f094bd5107742ae1f494acb06f71"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEPENDS:append = " libhandlebars glib-2.0 libsoup-3.0 glib-2.0-native"
RDEPENDS:${PN} += "libhandlebars glib-2.0"

FILES:${PN} += "\
${datadir}/dbus-1/system.d/${PN}.conf \
${datadir}/${PN}/index.html.hbs \
${datadir}/${PN}/style.css \
"

inherit meson pkgconfig

###############################################################################
