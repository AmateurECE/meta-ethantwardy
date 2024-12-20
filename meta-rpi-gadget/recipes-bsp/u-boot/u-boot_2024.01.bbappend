FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# TODO: Generate these (this?) file at build-time.
NETBOOT_SOURCES = "\
file://0001-rpi-Add-netboot-gadget-environment-file.patch \
file://netboot.cfg \
"

SRC_URI += "\
${@bb.utils.contains('IMAGE_FEATURES', 'netboot', '${NETBOOT_SOURCES}', '', d)} \
"
