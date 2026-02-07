SUMMARY = "A consumer library interface to DWARF"
LICENSE = "LGPL-2.1-or-later & GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=16ea26ea5a08cadb982cc10c28a521fd"

SRC_URI = " \
    http://www.prevanders.net/libdwarf-${PV}.tar.xz \
"

SRC_URI[sha256sum] = "b5be211b1bd0c1ee41b871b543c73cbff5822f76994f6b160fc70d01d1b5a1bf"

DEPENDS = "elfutils"

S = "${UNPACKDIR}/libdwarf-${PV}"

inherit autotools pkgconfig

FILES:${PN} += " \
    /usr/share/dwarfdump \
    /usr/share/dwarfdump/dwarfdump.conf \
"

BBCLASSEXTEND += "native nativesdk"
