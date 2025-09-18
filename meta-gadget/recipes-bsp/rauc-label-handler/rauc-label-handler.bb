SUMMARY = "Keep slot labels in sync after a RAUC update"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "file://rauc-label-handler.sh"

S = "${UNPACKDIR}/sources"

RDEPENDS:${PN} += "e2fsprogs-tune2fs"

do_install() {
    install -Dm755 ${UNPACKDIR}/rauc-label-handler.sh ${D}${libexecdir}/rauc/rauc-label-handler
}
