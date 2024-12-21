FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:netboot = " \
file://0001-rpi-Add-netboot-gadget-environment-file.patch \
file://netboot.cfg \
"

do_configure:append:netboot() {
    # Use the u-boot config to propagate serverip
    if [ -n "${NETBOOT_SERVER_IP}" ]; then
        printf '%s\n' \
            "CONFIG_SERVERIP=\"${NETBOOT_SERVER_IP}\"" \
            "CONFIG_USE_SERVERIP=y" \
            "CONFIG_BOOTP_SERVERIP=y" \
            "CONFIG_BOOTP_PREFER_SERVERIP=y" \
            >> ${B}/.config
    fi
}
