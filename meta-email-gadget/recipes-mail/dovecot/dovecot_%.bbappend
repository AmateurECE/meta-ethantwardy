FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://dovecot.sh \
"

do_install:append() {
    install -Dm755 ${UNPACKDIR}/dovecot.sh ${D}${sysconfdir}/init.d/${PN}

    # Don't allow dovecot to install a configuration file, since ours is
    # provided by the dovecot-conf recipe.
    rm -f ${D}/etc/dovecot/dovecot.conf
}

inherit update-rc.d

INITSCRIPT_NAME = "dovecot"
