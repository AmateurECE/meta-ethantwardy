###############################################################################
# NAME:             libfsautomata_${PV}.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Yocto recipe for libfsautomata (a static library)
#
# CREATED:          11/07/2021
#
# LAST EDITED:      11/07/2021
###

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=965dbcfdcce8f2faadb4c6f8118ff50a"

SRC_URI = "git://github.com/AmateurECE/fsautomata;protocol=https;branch=trunk"

PV = "0.1.0"
SRCREV = "714a18591c387c235a94f7b37306790c975ef40c"
PROVIDES = "libfsautomata-staticdev"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit meson

###############################################################################
