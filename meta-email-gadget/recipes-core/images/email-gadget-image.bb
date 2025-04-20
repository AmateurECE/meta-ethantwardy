require recipes-core/images/core-image-minimal.bb

# Install some stuff I want
IMAGE_INSTALL += " \
    curl \
    man-db \
    bash \
    net-tools \
    gadget-tools \
    kernel-module-configs \
"

# Install a few things that provide required features
IMAGE_INSTALL += " \
    sudo \
    tzdata \
    ntp \
    packagegroup-core-ssh-openssh \
"

# Firewall support
IMAGE_INSTALL += " \
    nftables \
    firewall-config \
    kernel-module-nf-tables \
    kernel-module-nft-ct \
"

# Mail documentation, utilities and services.
IMAGE_INSTALL += " \
    postfix \
    postfix-doc \
    dovecot \
    bsd-mailx \
    mutt \
    rspamd \
"

# Bind9 as a recursive DNS nameserver
IMAGE_INSTALL += " bind"

# Build configuration at runtime
IMAGE_INSTALL += " make-conf"

inherit gadget-image
