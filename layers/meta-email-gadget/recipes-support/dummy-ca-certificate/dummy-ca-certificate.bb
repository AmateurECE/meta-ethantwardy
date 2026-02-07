SUMMARY = "Dummy CA Certificate used for the test server"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "https://static.ethantwardy.com/dummy-ca-certificate.crt"
SRC_URI[sha256sum] = "c2c124d375b01914d9d87b850dc751ffa7605fbaadfa751415512fdcc97a6a38"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
    install -Dm644 ${UNPACKDIR}/dummy-ca-certificate.crt -t ${D}/usr/local/share/ca-certificates
}

FILES:${PN} = "/usr/local/share/ca-certificates/dummy-ca-certificate.crt"
