require recipes-core/images/core-image-minimal.bb

IMAGE_FSTYPES = "wic"
WKS_FILE = "pcbios-gadget.wks"

# Bind9 as a recursive DNS nameserver
IMAGE_INSTALL += " bind"

inherit gadget-image
