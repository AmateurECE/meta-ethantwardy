LICENSE = "MIT & LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=7608a988a3d5cbe1f211ac57ed09d30a \
                    file://aapl/COPYING;md5=931104d082e5e3f271202c909d0aea27"

SRC_URI = "https://www.colm.net/files/ragel/ragel-${PV}.tar.gz"
SRC_URI[sha256sum] = "5f156edb65d20b856d638dd9ee2dfb43285914d9aa2b6ec779dac0270cd56c3f"

# NOTE: the following prog dependencies are unknown, ignoring: javac pdflatex
# ruby txl gmcs kelbt fig2dev go gdc ragel

# NOTE: if this software is not capable of being built in a separate build
# directory from the source, you should replace autotools with
# autotools-brokensep in the inherit line
inherit autotools

BBCLASSEXTEND += "native nativesdk"
