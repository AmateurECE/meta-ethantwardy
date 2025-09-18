SUMMARY = "Firewall configuration for the email server"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}"

SRC_URI = " \
    file://nftables.conf \
    file://firewall.sh \
"

do_install() {
    install -Dm755 ${UNPACKDIR}/nftables.conf -t ${D}${sysconfdir}/nftables
    install -Dm755 ${UNPACKDIR}/firewall.sh ${D}${sysconfdir}/init.d/firewall
}

RDEPENDS:${PN} += "nftables"

inherit update-rc.d

INITSCRIPT_NAME = "firewall"
