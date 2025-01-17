FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = " file://fstab"
FILES:${PN} += "/data"

do_install:append() {
    install -d ${D}/data
}
