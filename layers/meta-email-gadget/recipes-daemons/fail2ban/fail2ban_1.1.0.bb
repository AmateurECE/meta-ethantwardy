SUMMARY = "Ban hosts that cause multiple authentication errors"
HOMEPAGE = "https://fail2ban.org/"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=ecabc31e90311da843753ba772885d9f"

inherit setuptools3

SRC_URI = " \
    git://github.com/fail2ban/fail2ban.git;protocol=https;branch=master;tag=${PV} \
    file://fail2ban.sh \
    file://jail.conf \
    file://sshd.conf \
"
SRC_URI[sha256sum] = "a867bfbb5126516c12d4c8a93909ef1e4d5309fc4e9f5b97b2d987b0ffd4bbe3"

do_install:append() {
    ln -sf python3 ${D}${bindir}/fail2ban-python

    install -Dm755 ${UNPACKDIR}/fail2ban.sh ${D}${sysconfdir}/init.d/fail2ban

    # Data is installed into /usr/lib
    mv ${D}${libdir}/*/site-packages${sysconfdir}/* ${D}${sysconfdir}
    mv ${D}${libdir}/*/site-packages${datadir}/* ${D}${datadir}

    # Overwrite these files, which are customized for the email-gadget
    install -Dm644 ${UNPACKDIR}/jail.conf -t ${D}${sysconfdir}/fail2ban
    install -Dm644 ${UNPACKDIR}/sshd.conf -t ${D}${sysconfdir}/fail2ban/filter.d
}

# TODO: Is there a better way than the kernel module?
RDEPENDS:${PN} += " \
    python3-sqlite3 \
    iptables \
    kernel-module-xt-multiport \
    kernel-module-ipt-reject \
"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "fail2ban"

inherit update-rc.d

BBCLASSEXTEND = "native"
