inherit systemd

SYSTEMD_SERVICE:${PN} = "nftables.service"

# TODO: This should probably be a patch to the recipe.
do_install:append() {
    install -Dm644 ${B}/tools/nftables.service -t ${D}${systemd_system_unitdir}
}

FILES:${PN} += "${systemd_system_unitdir}/nftables.service"
