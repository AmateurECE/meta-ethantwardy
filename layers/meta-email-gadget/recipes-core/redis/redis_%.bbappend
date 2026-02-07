FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://redis.conf \
    file://redis-server.sh \
    file://redis-rspamd.sh \
    file://redis-bayesian.sh \
    file://rspamd.conf \
    file://bayesian.conf \
"

# Redis recipe sets these. Pedantically unset them here.
python __anonymous__ () {
    d.delVar("INITSCRIPT_NAME")
    d.delVar("INITSCRIPT_PARAMS")
}

PACKAGES =+ "${PN}-rspamd ${PN}-bayesian"
RDEPENDS:${PN}:class-target += "${PN}-rspamd ${PN}-bayesian"

INITSCRIPT_PACKAGES = "${PN}-rspamd ${PN}-bayesian"
INITSCRIPT_NAME:${PN}-rspamd = "redis-rspamd"
INITSCRIPT_NAME:${PN}-bayesian = "redis-bayesian"

CONFFILES:${PN}-rspamd = "${sysconfdir}/redis/rspamd.conf"
CONFFILES:${PN}-bayesian = "${sysconfdir}/redis/bayesian.conf"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/redis.conf -t ${D}/etc/redis
    install -Dm755 ${UNPACKDIR}/redis-server.sh -t ${D}/usr/libexec/redis
    # Delete the default initscript installed by upstream
    rm -f ${D}/etc/init.d/redis-server

    install -Dm755 ${UNPACKDIR}/redis-rspamd.sh ${D}/etc/init.d/redis-rspamd
    install -Dm644 ${UNPACKDIR}/rspamd.conf -t ${D}/etc/redis
    install -d -m755 ${D}/var/lib/redis/rspamd

    install -Dm755 ${UNPACKDIR}/redis-bayesian.sh ${D}/etc/init.d/redis-bayesian
    install -Dm644 ${UNPACKDIR}/bayesian.conf -t ${D}/etc/redis
    install -d -m755 ${D}/var/lib/redis/bayesian
}

pkg_postinst:${PN}-rspamd() {
    chown redis:redis $D/var/lib/redis/rspamd
}

pkg_postinst:${PN}-bayesian() {
    chown redis:redis $D/var/lib/redis/bayesian
}

FILES:${PN}-rspamd = " \
    /etc/init.d/redis-rspamd \
    /etc/redis/rspamd.conf \
    /var/lib/redis/rspamd \
"

FILES:${PN}-bayesian = " \
    /etc/init.d/redis-bayesian \
    /etc/redis/bayesian.conf \
    /var/lib/redis/bayesian \
"
