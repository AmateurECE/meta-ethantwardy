DESCRIPTION = "U-boot environment for gadgets with RAUC"
LICENSE = "MIT"

B = "${WORKDIR}/build"

do_configure_env() {
    set_envvar boot_targets mmc
}

inherit u-boot-env
