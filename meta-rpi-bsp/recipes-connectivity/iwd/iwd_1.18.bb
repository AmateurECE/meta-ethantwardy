# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   COPYING
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=fb504b67c50331fc78734fed90fb0e09"

SRC_URI = "\
git://git.kernel.org/pub/scm/network/wireless/iwd.git;protocol=https \
file://iwd \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Modify these as desired
PV = "1.18"
SRCREV = "1.18"

S = "${WORKDIR}/git"

# NOTE: the following prog dependencies are unknown, ignoring: rst2man.py rst2man
# NOTE: the following library dependencies are unknown, ignoring: ubsan asan lsan
#       (this is based on recipes that have previously been built and packaged)
DEPENDS = "readline ell"

# NOTE: if this software is not capable of being built in a separate build directory
# from the source, you should replace autotools with autotools-brokensep in the
# inherit line
inherit pkgconfig autotools-brokensep update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "iwd"

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = "\
--disable-systemd-service \
--enable-external-ell \
--disable-manual-pages \
"

FILES_${PN} += "\
${datadir}/dbus-1/system.d/${PN}-dbus.conf \
"

do_configure() {
    autoupdate
    ./bootstrap
    autotools_do_configure
}

do_install_append() {
    install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/iwd ${D}${INIT_D_DIR}/${PN}
}
