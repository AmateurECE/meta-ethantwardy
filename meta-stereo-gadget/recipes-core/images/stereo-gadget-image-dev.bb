require recipes-core/images/stereo-gadget-image.bb

# Debug packages
IMAGE_INSTALL:append = " \
ldd \
gdbserver \
"

EXTRA_IMAGE_FEATURES += "debug-tweaks"

WKS_FILE = "netboot-sdcard.wks"

inherit buildhistory
