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

SRC_URI = "git://git.kernel.org/pub/scm/libs/ell/ell.git;protocol=https;branch=master"

# Modify these as desired
PV = "0.44"
SRCREV = "0.44"

S = "${WORKDIR}/git"

# NOTE: the following prog dependencies are unknown, ignoring: openssl xxd
# NOTE: the following library dependencies are unknown, ignoring: asan ubsan lsan
#       (this is based on recipes that have previously been built and packaged)
DEPENDS = "glib-2.0"

# NOTE: if this software is not capable of being built in a separate build directory
# from the source, you should replace autotools with autotools-brokensep in the
# inherit line
inherit pkgconfig autotools-brokensep

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = ""

FILES:${PN} += "${includedir}/ell/useful.h"

do_configure() {
    echo "$PWD"
    ./bootstrap
    oe_runconf
}

do_install:append() {
    install ${S}/${PN}/useful.h ${D}${includedir}/${PN}
}
