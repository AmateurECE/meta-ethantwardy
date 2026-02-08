FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://redis.conf \
    file://rspamd.conf \
    file://bayesian.conf \
    file://redis@.service \
    file://sysctl.conf \
    file://tmpfiles.conf \
"

# Redis recipe sets this--unset it so that we can install our own
SYSTEMD_SERVICE:${PN} = ""

PACKAGES =+ "${PN}-rspamd ${PN}-bayesian"
RDEPENDS:${PN}:class-target += "${PN}-rspamd ${PN}-bayesian"

CONFFILES:${PN}-rspamd = "${sysconfdir}/redis/rspamd.conf"
CONFFILES:${PN}-bayesian = "${sysconfdir}/redis/bayesian.conf"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/redis@.service -t ${D}${systemd_system_unitdir}
    install -d ${D}${systemd_system_unitdir}/rspamd.service.requires
    install -Dm644 ${UNPACKDIR}/redis.conf -t ${D}/etc/redis
    install -Dm644 ${UNPACKDIR}/tmpfiles.conf ${D}/usr/lib/tmpfiles.d/redis.conf
    # Delete the default systemd script installed by upstream
    rm -f ${D}${systemd_system_unitdir}/redis.service

    install -Dm644 ${UNPACKDIR}/rspamd.conf -t ${D}/etc/redis
    ln -s ../redis@.service ${D}${systemd_system_unitdir}/rspamd.service.requires/redis@rspamd.service
    install -d -m755 ${D}/var/lib/redis/rspamd

    install -Dm644 ${UNPACKDIR}/bayesian.conf -t ${D}/etc/redis
    ln -s ../redis@.service ${D}${systemd_system_unitdir}/rspamd.service.requires/redis@bayesian.service
    install -d -m755 ${D}/var/lib/redis/bayesian

    install -Dm644 ${UNPACKDIR}/sysctl.conf ${D}/usr/lib/sysctl.d/redis.conf
}

pkg_postinst:${PN}-rspamd() {
    chown redis:rspamd $D/var/lib/redis/rspamd
}

pkg_postinst:${PN}-bayesian() {
    chown redis:rspamd $D/var/lib/redis/bayesian
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/redis@.service \
    /usr/lib/tmpfiles.d/redis.conf \
    /usr/lib/sysctl.d/redis.conf \
"

FILES:${PN}-rspamd = " \
    ${systemd_system_unitdir}/rspamd.service.requires/redis@rspamd.service \
    /etc/redis/rspamd.conf \
    /var/lib/redis/rspamd \
"

FILES:${PN}-bayesian = " \
    ${systemd_system_unitdir}/rspamd.service.requires/redis@bayesian.service \
    /etc/redis/bayesian.conf \
    /var/lib/redis/bayesian \
"
