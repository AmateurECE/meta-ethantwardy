FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'rauc', 'file://verity.cfg', '', d)} \
"
