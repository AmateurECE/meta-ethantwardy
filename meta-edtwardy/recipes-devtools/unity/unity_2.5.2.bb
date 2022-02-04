###############################################################################
# NAME:             unity_${PV}.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Yocto recipe for unity (a unit-testing framework for C)
#
# CREATED:          01/16/2022
#
# LAST EDITED:      01/16/2022
###

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=a8efac2e1388b8d17af2cdf060cf6c10"

SRC_URI = "git://github.com/ThrowTheSwitch/Unity;protocol=https;branch=master"

SRCREV = "v${PV}"
PROVIDES = "unity-staticdev"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

EXTRA_OECMAKE = "-DUNITY_EXTENSION_FIXTURE=true"

inherit cmake

###############################################################################
