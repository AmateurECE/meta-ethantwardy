FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://named.conf.options"

do_install:append() {
    install -m644 ${UNPACKDIR}/named.conf.options -t ${D}/etc/bind

    # allow-query can be specified in the local options instead (used for
    # testing)
    if [ -n $BIND_LOCAL_CLIENT_WHITELIST ]; then
        sed -i -e '/allow-query/d' ${D}/etc/bind/named.conf.options
    fi

    echo 'nameserver 127.0.0.1' > ${D}/etc/resolv.conf
}

FILES:${PN} += " /etc/resolv.conf"
