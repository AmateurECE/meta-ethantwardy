SUMMARY = "U-boot boot script for RAUC-based A/B systems"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
COMPATIBLE_MACHINE = "^rpi$"

DEPENDS = "u-boot-mkimage-native"

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "file://boot.cmd"

do_compile() {
    mkimage -A ${UBOOT_ARCH} -T script -C none -n "Boot script" -d \
        "${S}/boot.cmd" ${B}/boot.scr
}

inherit kernel-arch deploy nopackages

S = "${UNPACKDIR}"

do_deploy() {
    install -d ${DEPLOYDIR}
    install -m 0644 ${B}/boot.scr ${DEPLOYDIR}
}

addtask do_deploy after do_compile before do_build

PROVIDES += "u-boot-default-script"
