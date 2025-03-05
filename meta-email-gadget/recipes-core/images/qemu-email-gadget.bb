require recipes-core/images/core-image-minimal.bb

IMAGE_FSTYPES = "wic"
WKS_FILE = "pcbios-gadget.wks"

IMAGE_INSTALL += " curl postfix bsd-mailx"

inherit gadget-image
