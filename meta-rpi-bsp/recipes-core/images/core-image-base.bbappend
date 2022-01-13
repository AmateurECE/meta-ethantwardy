
IMAGE_INSTALL:append = " \
alsa-utils-aplay \
alsa-utils-speakertest \
bluez-alsa \
iwd \
ldd \
bluez-iot-agent \
gdbserver \
poky-player-wp-config \
packagegroup-pipewire \
${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd-networkd-conf', '', d)} \
${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'pipewire-bluez5', '', d)} \
pipewire-alsa-card-profile \
"
