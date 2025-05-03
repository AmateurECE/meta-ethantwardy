IMAGE_FEATURES += "read-only-rootfs"
IMAGE_FEATURES += "${@'overlayfs-etc' if 'ab-mutable' in d.getVar('OVERRIDES') else ''}"

do_image_wic[depends] += "${@'virtual/u-boot-env:do_deploy' if 'raspberrypi4' in d.getVar('OVERRIDES') else ''}"

do_image[depends] += "${@'cloud-boot-image:do_image_complete' if 'cloud-mutable-gadget' in d.getVar('OVERRIDES') else ''}"
