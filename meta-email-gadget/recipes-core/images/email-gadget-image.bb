require recipes-core/images/core-image-minimal.bb

# Install some stuff I want
IMAGE_INSTALL += " \
    curl \
    man-db \
    bash \
    net-tools \
    gadget-tools \
    kernel-module-configs \
    util-linux \
"

# Install a few things that provide required features
IMAGE_INSTALL += " \
    sudo \
    tzdata \
    busybox-ntpd \
    packagegroup-core-ssh-openssh \
    wireguard-tools \
    cronie \
    certbot \
    certbot-dns-luadns \
"

# Ensure the update-rc.d and shadow packages are not removed from the rootfs.
# These packages are needed for local service and user management.
ROOTFS_RO_UNNEEDED:remove = "update-rc.d shadow"

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
    spam-statistics \
"

# Bind9 as a recursive DNS nameserver
IMAGE_INSTALL += " bind"

# Build configuration at runtime
IMAGE_INSTALL += " make-conf"

# Nginx as a webserver
IMAGE_INSTALL += "nginx"

inherit gadget-image
