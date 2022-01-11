FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://pipewire-override.service"

FILES:${PN}:append = " \
/lib/systemd/system/pipewire.socket \
/lib/systemd/system/pipewire.service \
${sysconfdir}/systemd/system/pipewire.service.d/override.conf \
"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system
    install -d ${D}${sysconfdir}/systemd/system/pipewire.service.d
    install -m 0644 ${WORKDIR}/pipewire-override.service ${D}${sysconfdir}/systemd/system/pipewire.service.d/override.conf
}
