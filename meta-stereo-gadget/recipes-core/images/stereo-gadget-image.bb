
SUMMARY = "Bluetooth gadget for my stereo system"

LICENSE = "MIT"

# The minimum necessary to boot
IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_INSTALL:append = " \
alsa-utils-aplay \
alsa-utils-speakertest \
iwd \
${VIRTUAL-RUNTIME_wireplumber-config} \
pipewire \
${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd-networkd-conf', '', d)} \
pipewire-alsa-card-profile \
"

# TODO: Might be able to minimize this a little bit more
IMAGE_INSTALL:append = " \
linux-firmware-rpidistro-bcm43455 \
linux-firmware-rpidistro-bcm43456 \
bluez-firmware-rpidistro-bcm4345c0-hcd \
bluez-firmware-rpidistro-bcm4345c5-hcd \
kernel-modules \
"

IMAGE_FEATURES += "ssh-server-dropbear"

IMAGE_FSTYPES = "tar.gz wic"

inherit core-image
