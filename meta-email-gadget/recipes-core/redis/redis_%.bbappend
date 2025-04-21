FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://redis.conf \
    file://redis-server.sh \
"

do_install:append() {
    install -Dm644 ${UNPACKDIR}/redis.conf -t ${D}/etc/redis
    install -Dm755 ${UNPACKDIR}/redis-server.sh ${D}/etc/init.d/redis-server
}
