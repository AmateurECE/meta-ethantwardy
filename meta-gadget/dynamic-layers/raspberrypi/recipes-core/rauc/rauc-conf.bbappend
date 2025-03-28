FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

do_install:append() {
    # Mountpoint needed for the installation process at runtime.
    install -d ${D}/mnt/rauc/bundle
    install -d ${D}/mnt/rauc/rootfs.0
    install -d ${D}/mnt/rauc/rootfs.1
}

FILES:${PN} += " \
    /mnt/rauc/bundle \
    /mnt/rauc/rootfs.0 \
    /mnt/rauc/rootfs.1 \
"
