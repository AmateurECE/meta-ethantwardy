
IMAGE_INSTALL:append = " \
                     alsa-utils-aplay \
                     alsa-utils-speakertest\
                     bluez-alsa \
                     iwd \
                     ldd \
                     bluez-iot-agent \
                     gdbserver \
                     packagegroup-pipewire \
                     ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd-networkd-conf', '', d)} \
                     "
