###############################################################################
# NAME:             libhandlebars_${PV}.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Recipe for libhandlebars
#
# CREATED:          11/28/2021
#
# LAST EDITED:      01/16/2022
###

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97e4de027b3766fc2928be5a56f7aacf"

SRC_URI="git://github.com/AmateurECE/libhandlebars;protocol=https;branch=trunk"
SRCREV = "v${PV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEPENDS:append = " cmake-native unity-staticdev"
RPROVIDES:${PN} += "libhandlebars"

inherit meson pkgconfig

do_write_config:append() {
    # As of last edited date, meson.bbclass does not populate CMake variables
    # in meson.cross file. Unfortunately, unity exports unityConfig.cmake, but
    # no .pc file. So, manually add the right configuration to meson.cross to
    # allow meson to locate unity using CMake.
    sed -i "2i cmake = '${WORKDIR}/recipe-sysroot-native/usr/bin/cmake'" \
        ${WORKDIR}/meson.cross
    sed -i "/\[built-in options\]/a cmake_prefix_path = '${WORKDIR}/recipe-sysroot/usr'" ${WORKDIR}/meson.cross
}

###############################################################################
