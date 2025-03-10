require recipes-core/images/core-image-minimal.bb

IMAGE_FSTYPES = "wic"
WKS_FILE = "pcbios-gadget.wks"

# Install some stuff I want
IMAGE_INSTALL += " \
    curl \
    man-db \
    bash \
"

# Mail documentation, utilities and services.
IMAGE_INSTALL += " postfix bsd-mailx postfix-doc"

# Bind9 as a recursive DNS nameserver
IMAGE_INSTALL += " bind"

# Build configuration at runtime
IMAGE_INSTALL += " make-conf"

inherit gadget-image
