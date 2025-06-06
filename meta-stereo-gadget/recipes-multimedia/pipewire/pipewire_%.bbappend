FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://pipewire.sh \
    file://launch-pipewire.sh \
    file://10-schiit-modius-dac.conf \
"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "pipewire"

inherit update-rc.d

FILES:${PN}:append = " \
    ${sysconfdir}/init.d/${INITSCRIPT_NAME} \
    ${libexecdir}/launch-pipewire.sh \
    ${datadir}/pipewire/pipewire.conf.d/10-schiit-modius-dac.conf \
"

do_install:append() {
    install -Dm 0755 ${UNPACKDIR}/pipewire.sh ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    install -Dm 0755 ${UNPACKDIR}/launch-pipewire.sh -t ${D}${libexecdir}
    install -Dm 0644 ${UNPACKDIR}/10-schiit-modius-dac.conf -t ${D}${datadir}/pipewire/pipewire.conf.d
}
