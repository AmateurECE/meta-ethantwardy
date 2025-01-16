require recipes-core/images/stereo-gadget-image.bb

IMAGE_FEATURES += "empty-root-password"

inherit netboot-image
