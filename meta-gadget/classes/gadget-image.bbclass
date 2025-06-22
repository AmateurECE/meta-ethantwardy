IMAGE_FEATURES += "read-only-rootfs"
IMAGE_FEATURES += "${@'overlayfs-etc' if 'ab-mutable' in d.getVar('OVERRIDES') else ''}"

# Gadgets based on the qemux86-64 machine depend on this file in their WKS script.
SRC_URI:append:qemux86-64 = " file://${@bb.utils.which(d.getVar('BBPATH', True), 'wic/qemu-mutable-gadget-grub.cfg')}"

do_image_wic[depends] += "${@'virtual/u-boot-env:do_deploy' if 'raspberrypi4' in d.getVar('OVERRIDES') else ''}"

do_image[depends] += "${@'cloud-boot-image:do_image_complete' if 'cloud-mutable-gadget' in d.getVar('OVERRIDES') else ''}"

inherit_defer ${@bb.utils.contains('INITRAMFS_IMAGE_BUNDLE', '1', 'kernel-artifact-names', '', d)}

# The kernel-image package contains the kernel image without the initramfs. If
# an initramfs image is bundled with the kernel, we want to boot that one
# instead.
boot_bundled_kernel() {
    install -Dm644 ${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}-${INITRAMFS_LINK_NAME}.bin -t ${IMAGE_ROOTFS}/boot
    ln -sf ${KERNEL_IMAGETYPE}-${INITRAMFS_LINK_NAME}.bin ${IMAGE_ROOTFS}/boot/${KERNEL_IMAGETYPE}
}
ROOTFS_POSTPROCESS_COMMAND += "${@bb.utils.contains('INITRAMFS_IMAGE_BUNDLE', '1', 'boot_bundled_kernel', '', d)}"

do_rootfs[depends] += "${@bb.utils.contains('INITRAMFS_IMAGE_BUNDLE', '1', 'virtual/kernel:do_bundle_initramfs', '', d)}"
