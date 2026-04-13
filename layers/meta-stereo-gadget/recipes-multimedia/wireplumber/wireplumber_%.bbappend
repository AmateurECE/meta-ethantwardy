FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://wireplumber-override.service"

FILES:${PN}:append = " \
    ${sysconfdir}/systemd/system/wireplumber.service.d/override.conf \
"

do_install:append() {
    install -Dm 0644 ${UNPACKDIR}/wireplumber-override.service \
        ${D}${sysconfdir}/systemd/system/wireplumber.service.d/override.conf
    # TODO: I don't understand why, but this is the only way to get
    # wireplumber to start on boot.
    install -d ${D}${systemd_system_unitdir}/pipewire.service.wants
    ln -s ${systemd_system_unitdir}/${PN}.service \
        ${D}${systemd_system_unitdir}/pipewire.service.wants/${PN}.service
}
