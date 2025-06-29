include recipes-core/images/core-image-minimal.bb

# Required (or desired) tools
IMAGE_INSTALL += " \
    curl \
    bash \
    net-tools \
    util-linux \
    packagegroup-core-ssh-openssh \
    tzdata \
"

# Ethernet drivers
IMAGE_INSTALL += " \
    kernel-module-r8169 \
    kernel-module-realtek \
"

# Temperature Monitoring Driver
IMAGE_INSTALL += "kernel-module-k10temp"

IMAGE_INSTALL += " \
    backup-dataset \
"

IMAGE_INSTALL += " \
    ${MACHINE_EXTRA_RRECOMMENDS} \
"

inherit gadget-image
