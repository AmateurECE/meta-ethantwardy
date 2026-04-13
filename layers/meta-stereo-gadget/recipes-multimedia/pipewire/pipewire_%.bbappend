FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://pipewire-override.service \
    file://pipewire.conf \
    file://10-schiit-modius-dac.conf \
"

FILES:${PN}:append = " \
    ${sysconfdir}/systemd/system/pipewire.service.d/override.conf \
    ${datadir}/dbus-1/system.d/pipewire.conf \
    ${datadir}/pipewire/pipewire.conf.d/10-schiit-modius-dac.conf \
"

do_install:append() {
    install -Dm 0644 ${UNPACKDIR}/pipewire-override.service \
        ${D}${sysconfdir}/systemd/system/pipewire.service.d/override.conf
    install -Dm 0644 ${UNPACKDIR}/pipewire.conf \
        -t ${D}${datadir}/dbus-1/system.d
    install -Dm 0644 ${UNPACKDIR}/10-schiit-modius-dac.conf \
        -t ${D}${datadir}/pipewire/pipewire.conf.d
}
