inherit allarch

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SUMMARY = "Terminfo files for the kitty terminal emulator"

SRC_URI += " \
    git://github.com/kovidgoyal/kitty;protocol=https;branch=master;tag=v${PV} \
"

INHIBIT_DEFAULT_DEPS = "1"

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -Dm644 ${S}/terminfo/x/xterm-kitty -t ${D}/usr/share/terminfo/x
}

FILES:${PN} = "/usr/share/terminfo/x/xterm-kitty"
