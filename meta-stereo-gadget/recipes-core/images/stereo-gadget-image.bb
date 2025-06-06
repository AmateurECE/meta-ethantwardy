SUMMARY = "Bluetooth gadget for my stereo system"
LICENSE = "MIT"

require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL:append = " \
    alsa-utils-aplay \
    alsa-utils-speakertest \
    iwd \
    pipewire \
    pipewire-tools \
    pipewire-alsa-card-profile \
    packagegroup-core-ssh-openssh \
    pi-bluetooth \
    busybox-ntpd \
    usbutils \
"

# TODO: Can probably slim this down by selecting a MACHINE_FEATURE?
IMAGE_INSTALL:append = " \
    linux-firmware-rpidistro-bcm43430 \
    linux-firmware-rpidistro-bcm43436 \
    linux-firmware-rpidistro-bcm43436s \
    linux-firmware-rpidistro-bcm43439 \
    linux-firmware-rpidistro-bcm43455 \
    linux-firmware-rpidistro-bcm43456 \
    linux-firmware-rpidistro-license \
    bluez-firmware-rpidistro-cypress-license \
    bluez-firmware-rpidistro-bcm43430a1-hcd \
    bluez-firmware-rpidistro-bcm43430b0-hcd \
    bluez-firmware-rpidistro-bcm4343a2-hcd \
    bluez-firmware-rpidistro-bcm4345c0-hcd \
    bluez-firmware-rpidistro-bcm4345c5-hcd \
    kernel-modules \
"

IMAGE_FSTYPES = "tar.bz2 wic.bz2 wic.bmap"

inherit gadget-image
