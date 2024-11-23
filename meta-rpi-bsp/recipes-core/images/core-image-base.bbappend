
IMAGE_INSTALL:append = " \
alsa-utils-aplay \
alsa-utils-speakertest \
iwd \
ldd \
bluez-iot-agent \
gdbserver \
poky-player-wp-config \
pipewire \
${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd-networkd-conf', '', d)} \
pipewire-alsa-card-profile \
"
