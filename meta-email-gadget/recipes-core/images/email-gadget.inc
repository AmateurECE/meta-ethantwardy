require recipes-core/images/core-image-minimal.bb

# Install some stuff I want
IMAGE_INSTALL += " \
    curl \
    man-db \
    bash \
    net-tools \
    gadget-tools \
    sudo \
    tzdata \
"

# Mail documentation, utilities and services.
IMAGE_INSTALL += " \
    postfix \
    postfix-doc \
    dovecot \
    bsd-mailx \
    mutt \
"

# Bind9 as a recursive DNS nameserver
IMAGE_INSTALL += " bind"

# Build configuration at runtime
IMAGE_INSTALL += " make-conf"

inherit gadget-image
