SUMMARY = "Make configuration files at runtime"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "file://make-conf.sh"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

RDEPENDS:${PN} = "m4"

do_install() {
    install -Dm755 ${UNPACKDIR}/make-conf.sh ${D}${sysconfdir}/init.d/make-conf
}

inherit update-rc.d

INITSCRIPT_NAME = "make-conf"
