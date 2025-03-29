IMAGE_FEATURES += "read-only-rootfs"

do_image_wic[depends] += "virtual/u-boot-env:do_deploy"
