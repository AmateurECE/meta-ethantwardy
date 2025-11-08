SUMMARY = "Btrbk is a backup tool for btrfs subvolumes, taking advantage of \
    btrfs specific capabilities to create atomic snapshots and transfer them \
    incrementally to your backup locations."
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = " \
    git://github.com/digint/btrbk.git;protocol=https;branch=master;tag=v${PV} \
    file://0001-make-Don-t-install-doc-or-build-manpages.patch \
"

RDEPENDS:${PN} += " \
    bash \
    btrfs-tools \
    perl \
    ssh \
    mbuffer \
"

do_compile[noexec] = "1"

do_install () {
    oe_runmake install 'DESTDIR=${D}'
    if [ "${@bb.utils.contains('IMAGE_FEATURES', 'bash-completion', '1', '0', d)}" != "1" ]; then
        rm -rf ${D}${datadir}/bash-completion
    fi
    if [ "${@bb.utils.contains('DISTRO_FEATURES', 'systemd', '1', '0', d)}" != "1" ]; then
        rm -rf ${D}${libdir}
    fi
}
