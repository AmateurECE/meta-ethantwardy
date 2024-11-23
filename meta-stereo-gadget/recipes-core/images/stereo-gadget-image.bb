
SUMMARY = "Bluetooth gadget for my stereo system"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL:append = " \
alsa-utils-aplay \
alsa-utils-speakertest \
iwd \
poky-player-wp-config \
pipewire \
${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd-networkd-conf', '', d)} \
pipewire-alsa-card-profile \
"

# Debug packages
IMAGE_INSTALL:append = " \
ldd \
gdbserver \
"
