DESCRIPTION = "Creates a u-boot environment that configures U-boot for netbooting"
LICENSE = "MIT"

B = "${WORKDIR}/build"

python () {
    host = d.getVar('NETBOOT_TFTP_HOST')
    if not host:
        return

    if ':' in host:
        parts = host.split(':')
        d.setVar('NETBOOT_TFTP_IP', parts[0])
        d.setVar('NETBOOT_TFTP_PORT', parts[1])
    else:
        d.setVar('NETBOOT_TFTP_IP', host)
}

do_configure_env() {
    set_envvar boot_targets pxe
    set_envvar_if_nonempty serverip ${NETBOOT_TFTP_IP}
    set_envvar_if_nonempty tftpdstp ${NETBOOT_TFTP_PORT}
}

inherit u-boot-env
