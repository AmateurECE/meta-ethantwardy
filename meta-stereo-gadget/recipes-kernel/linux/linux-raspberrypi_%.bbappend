FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

unset KBUILD_DEFCONFIG
SRC_URI += "file://defconfig"
