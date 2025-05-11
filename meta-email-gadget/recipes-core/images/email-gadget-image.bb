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
    busybox-ntpd \
    packagegroup-core-ssh-openssh \
    wireguard-tools \
"

# Ensure the update-rc.d package is not removed from the rootfs
ROOTFS_RO_UNNEEDED:remove = "update-rc.d"

# Firewall support
IMAGE_INSTALL += " \
    nftables \
    firewall-config \
    kernel-module-nft-ct \
"

# Mail documentation, utilities and services.
IMAGE_INSTALL += " \
    postfix \
    postfix-doc \
    dovecot \
    dovecot-conf \
    pigeonhole \
    bsd-mailx \
    mutt \
    rspamd \
    redis \
"

# Bind9 as a recursive DNS nameserver
IMAGE_INSTALL += " bind"

# Build configuration at runtime
IMAGE_INSTALL += " make-conf"

inherit gadget-image
