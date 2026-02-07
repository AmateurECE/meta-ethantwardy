require recipes-core/images/core-image-minimal.bb

# Bind9 as a recursive DNS nameserver
IMAGE_INSTALL += " bind"

inherit gadget-image
