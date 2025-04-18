IMAGE_FEATURES += "read-only-rootfs"

do_image_wic[depends] += "${@bb.utils.contains('OVERRIDES', 'raspberrypi4', 'virtual/u-boot-env:do_deploy', '', d)}"
