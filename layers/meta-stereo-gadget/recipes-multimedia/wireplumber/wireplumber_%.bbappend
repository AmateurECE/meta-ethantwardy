FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://wireplumber.sh \
    file://launch-wireplumber.sh \
"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "wireplumber"

inherit update-rc.d

FILES:${PN}:append = " \
    ${libexecdir}/launch-pipewire.sh \
"

do_install:append() {
    install -Dm 0755 ${UNPACKDIR}/wireplumber.sh ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    install -Dm 0755 ${UNPACKDIR}/launch-wireplumber.sh -t ${D}${libexecdir}
}
