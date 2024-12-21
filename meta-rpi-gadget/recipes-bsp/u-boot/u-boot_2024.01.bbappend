FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# TODO: Generate these (this?) file at build-time.
SRC_URI:append:netboot = " \
file://0001-rpi-Add-netboot-gadget-environment-file.patch \
file://netboot.cfg \
"
