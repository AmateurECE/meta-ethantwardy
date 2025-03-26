FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://fw_env.config"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/fw_env.config -t ${D}/etc
}

FILES:${PN} += "/etc/fw_env.config"
