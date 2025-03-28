# This image's WIC file only contains a boot partition
WKS_FILE = "bootloader-only-image.wks"

# Build a rootfs tarball and a wic image (containing just the bootloader)
IMAGE_FSTYPES = "tar.gz wic"

# The netboot configuration lives in the environment for this gadget
PREFERRED_PROVIDER_virtual/u-boot-env = "u-boot-env-netboot"
do_image_wic[depends] += "virtual/u-boot-env:do_deploy"

inherit gadget-image
