SUMMARY = "Manage backups for the dataset"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${UNPACKDIR}"
SRC_URI += " \
    file://mount-dataset.sh \
"

do_install() {
    install -Dm755 ${UNPACKDIR}/mount-dataset.sh ${D}${sysconfdir}/init.d/mount-dataset
    install -d ${D}/dataset
}

inherit update-rc.d

INITSCRIPT_NAME = "mount-dataset"
INITSCRIPT_PARAMS = "start 5 3 4 5 . stop 30 0 1 6 ."

FILES:${PN} += " \
    ${sysconfdir}/init.d/mount-dataset \
    /dataset \
"
