# Images that netboot.
FEATURE_PACKAGES_netboot = ""

WKS_FILE = "netboot-sdcard.wks"

# Tell recipes to build for netbooting
IMAGE_FEATURES += "netboot"

# Build a rootfs tarball and a wic image (containing just the bootloader)
IMAGE_FSTYPES = "tar.gz wic"
