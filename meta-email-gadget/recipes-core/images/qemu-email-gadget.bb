require recipes-core/images/core-image-minimal.bb

IMAGE_FSTYPES = "wic"
WKS_FILE = "pcbios-gadget.wks"

inherit gadget-image
