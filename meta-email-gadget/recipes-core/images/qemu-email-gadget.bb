require recipes-core/images/core-image-minimal.bb

IMAGE_FSTYPES = "wic"
WKS_FILE = "pcbios-gadget.wks"

# Install curl, because it's better than wget
IMAGE_INSTALL += " curl"

# Mail documentation, utilities and services.
IMAGE_INSTALL += " postfix bsd-mailx postfix-doc"

IMAGE_INSTALL += " man-db"

inherit gadget-image
