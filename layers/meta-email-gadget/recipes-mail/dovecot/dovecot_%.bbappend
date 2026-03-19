FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

do_install:append() {
    # Don't allow dovecot to install a configuration file, since ours is
    # provided by the dovecot-conf recipe.
    rm -f ${D}/etc/dovecot/dovecot.conf

    # Our dovecot runs all the time, so don't install the socket.
    rm -f ${D}${systemd_system_unitdir}/dovecot.socket
}

# Upstream configures both the service and the socket. This causes Dovecot to
# complain on stderr. We just use the service.
SYSTEMD_SERVICE:${PN} = "dovecot.service"
# Upstream recipe disables the service by default.
SYSTEMD_AUTO_ENABLE = "enable"
