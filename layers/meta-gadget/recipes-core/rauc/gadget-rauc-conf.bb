SUMMARY = "RAUC system configuration for gadgets"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RAUC_KEYRING_FILE ??= "ca.cert.pem"
RAUC_KEYRING_URI ??= "file://${RAUC_KEYRING_FILE}"

RPROVIDES:${PN} += "virtual-rauc-conf"

INHIBIT_DEFAULT_DEPS = "1"
do_compile[noexec] = "1"

RAUC_CONFIG_FILE ?= ""
RAUC_CONFIG_URI = "file://${RAUC_CONFIG_FILE}"

SRC_URI = " \
    ${RAUC_CONFIG_URI} \
    ${RAUC_KEYRING_URI} \
"

S = "${UNPACKDIR}"

do_install () {
    if [ -z "${RAUC_CONFIG_FILE}" ]; then
        bbfatal "No RAUC configuration available for the current machine!"
    fi
    install -Dm0644 ${S}/${RAUC_CONFIG_FILE} ${D}${sysconfdir}/rauc/system.conf
    install -Dm0644 ${S}/${RAUC_KEYRING_FILE} -t ${D}${sysconfdir}/rauc

    # Mountpoint needed for the installation process at runtime.
    install -d ${D}/mnt/rauc/bundle
    install -d ${D}/mnt/rauc/rootfs.0
    install -d ${D}/mnt/rauc/rootfs.1
    install -d ${D}/var/rauc
}

FILES:${PN} += " \
    /mnt/rauc/bundle \
    /mnt/rauc/rootfs.0 \
    /mnt/rauc/rootfs.1 \
    /var/rauc \
"
