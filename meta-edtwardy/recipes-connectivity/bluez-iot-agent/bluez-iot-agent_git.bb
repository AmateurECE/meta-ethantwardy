###############################################################################
# NAME:             bluez-iot-agent_git.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Yocto recipe for the bluez-iot-agent project
#
# CREATED:          11/06/2021
#
# LAST EDITED:      02/04/2022
###

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
git://github.com/AmateurECE/bluez-iot-agent;protocol=https;branch=trunk \
file://${BPN} \
file://bluez-iot-agent.service \
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
${systemd_system_unitdir}/bluez-iot-agent.service \
"

inherit meson pkgconfig systemd

SYSTEMD_SERVICE:${PN} = "bluez-iot-agent.service"

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/bluez-iot-agent.service \
        ${D}${systemd_system_unitdir}
}

###############################################################################
