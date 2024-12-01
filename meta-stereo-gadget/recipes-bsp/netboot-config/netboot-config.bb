SUMMARY = "U-boot boot script for netbooting"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "u-boot-mkimage-native"

INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = "file://boot.cmd.in"

# The kernel devicetree file is often a path for most vendors (e.g. broadcom),
# but the deploydir for the image is flat, so we take the last path component.
FDT_FILE = "${@d.getVar('KERNEL_DEVICETREE').split()[0].split('/')[-1]}"

do_compile() {
    sed -e 's/@@NETBOOT_SERVER_IP@@/${NETBOOT_SERVER_IP}/' \
        -e 's/@@KERNEL_IMAGETYPE@@/${KERNEL_IMAGETYPE}/' \
        -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' \
        -e 's/@@MACHINE@@/${MACHINE}/' \
        -e 's/@@FDT_FILE@@/${FDT_FILE}/' \
        "${WORKDIR}/boot.cmd.in" > "${WORKDIR}/boot.cmd"
    mkimage -A ${UBOOT_ARCH} -T script -C none -n "Boot script" -d "${WORKDIR}/boot.cmd" boot.scr
}

inherit kernel-arch deploy nopackages

do_deploy() {
    install -Dm 0644 boot.scr -t ${DEPLOYDIR}
}

addtask do_deploy after do_compile before do_build

PROVIDES += "u-boot-default-script"
