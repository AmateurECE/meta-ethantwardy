include recipes-core/images/core-image-minimal.bb

# Required (or desired) tools
IMAGE_INSTALL += " \
    curl \
    bash \
    net-tools \
    util-linux \
    packagegroup-core-ssh-openssh \
"

# Ethernet drivers
IMAGE_INSTALL += " \
    kernel-module-r8169 \
    kernel-module-realtek \
"

# Temperature Monitoring Driver
IMAGE_INSTALL += "kernel-module-k10temp"

inherit gadget-image
