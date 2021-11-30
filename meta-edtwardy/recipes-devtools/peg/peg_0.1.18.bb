###############################################################################
# NAME:             peg_0.1.18.bb
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Recipe for peg--recursive descent parser generator for C
#
# CREATED:          11/28/2021
#
# LAST EDITED:      11/29/2021
###

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=dfde1a357e66b9849cf290d1e7e550f6"

SRC_URI = "https://www.piumarta.com/software/peg/peg-${PV}.tar.gz"
SRC_URI[md5sum] = "992fc7887afc2a8c92cdb1acb5b935e1"
SRC_URI[sha1sum] = "2390bcf91299aa61c5fa93895151ffeb988357a5"
SRC_URI[sha256sum] = "20193bdd673fc7487a38937e297fff08aa73751b633a086ac28c3b34890f9084"
SRC_URI[sha384sum] = "398f03ae3f4599adf7a43b970da0298ec2b379a4ef44f53d779dea8beb90d659b4a47f4432f19f4e4cfaf5cf272cdcff"
SRC_URI[sha512sum] = "ca2fb9088bf87955adf6f883370ddb7d5f6f3cae3605a871094317205a124cce4f7b9f83cf4662cb470e2c5a6977608b456eb1fad98022d7e40fc384d1fec0f8"

BBCLASSEXTEND = "native nativesdk"

PROVIDES:${PN} += "${PN}-native"

do_compile() {
        oe_runmake
}

do_install() {
        install -d ${D}${bindir}
        install ${S}/peg ${D}${bindir}
        install ${S}/leg ${D}${bindir}
}

###############################################################################
