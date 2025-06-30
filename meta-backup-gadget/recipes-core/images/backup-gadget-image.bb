include recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL += "${MACHINE_EXTRA_RRECOMMENDS}"

# Required (or desired) tools
IMAGE_INSTALL += " \
    curl \
    bash \
    net-tools \
    util-linux \
    packagegroup-core-ssh-openssh \
    tzdata \
"

# The function of this image.
IMAGE_INSTALL += "backup-dataset"

inherit gadget-image
