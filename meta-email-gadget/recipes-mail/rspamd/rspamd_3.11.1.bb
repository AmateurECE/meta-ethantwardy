SUMMARY = "Rapid spam filtering system"
LICENSE = "Apache-2.0 & BSL-1.0 & LGPL-3.0-only & MIT & CC0-1.0 & BSD-2-Clause & BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://LICENSE.md;md5=685e752980757c3fd342fe8f9938403c \
    file://contrib/aho-corasick/LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://contrib/ankerl/LICENSE;md5=1d7f9e1447a1ba175cfd5e005c801e89 \
    file://contrib/doctest/LICENSE.txt;md5=646df4c4443ce4b64f61e5f14cbe7acf \
    file://contrib/expected/COPYING;md5=65d3616852dbf7b1a6d4b53b00626032 \
    file://contrib/fmt/LICENSE.rst;md5=af88d758f75f3c5c48a967501f24384b \
    file://contrib/fpconv/LICENSE;md5=e4224ccaecb14d942c71d31bef20d78c \
    file://contrib/frozen/LICENSE;md5=f2c7a300e785f71cf2364e2176d8b3da \
    file://contrib/fu2/LICENSE.txt;md5=e4224ccaecb14d942c71d31bef20d78c \
    file://contrib/google-ced/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://contrib/hiredis/COPYING;md5=d84d659a35c666d23233e54503aaea51 \
    file://contrib/http-parser/LICENSE-MIT;md5=20d989143ee48a92dacde4f06bbcb59a \
    file://contrib/kann/LICENSE.txt;md5=c33d97b620153d084bdbed816d0acc3f \
    file://contrib/libev/LICENSE;md5=d6ad416afd040c90698edcdf1cbee347 \
    file://contrib/lua-argparse/LICENSE;md5=910fce0e89e2555047e2be1388279ece \
    file://contrib/lua-fun/COPYING.md;md5=c5b369c938ccc6dd77bbd7311963a979 \
    file://contrib/lua-lpeg/LICENSE;md5=5d3499d517d60a71b78f4a414e2170d5 \
    file://contrib/lua-lupa/LICENSE;md5=e8d598f46dbc1943360854dea43df683 \
    file://contrib/lua-tableshape/LICENSE;md5=5085fa140ef7cf81c1227ee661cc9f1a \
    file://contrib/replxx/LICENSE.md;md5=079313f5f68f241289ba4616b44a32b5 \
    file://contrib/simdutf/LICENSE-APACHE;md5=9a830e3d94d23c95cf7280a8e5d04741 \
    file://contrib/t1ha/LICENSE;md5=0da89f7aad111789c208343cb57bda94 \
    file://contrib/xxhash/LICENSE;md5=cb91c07001f1ca6fd50b6bd4f042946a \
    file://contrib/zstd/LICENSE;md5=c7f0b161edbe52f5f345a3d1311d0b32 \
    file://doc/doxydown/LICENSE;md5=916dfcd8ed63deb2466e53a6bb26a9b7 \
    file://src/libcryptobox/catena/LICENSE;md5=81c887b0161e9b3de79fee944e6b67b9 \
"

include rspamd.inc

inherit useradd update-rc.d

SRC_URI += " \
    file://rspamd.sh \
    file://classifier-bayes.conf \
    file://redis.conf \
    file://actions.conf \
    file://milter_headers.conf \
"

DEPENDS += "snowball-native"

RDEPENDS:${PN} += " \
    libsqlite3 \
    libssl \
"

SRCREV = "${PV}"

# TODO: Generated sources contain reference to TMPDIR
INSANE_SKIP:${PN}-src += "buildpaths"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN}  = "--system --home-dir /etc/rspamd --shell /bin/false rspamd"

EXTRA_OECMAKE += " \
    -DCONFDIR=/etc/rspamd \
    -DRUNDIR=/var/run \
    -DLOGDIR=/var/log \
"

do_install:append() {
    install -d -m755 ${D}/var/lib/rspamd
    install -Dm755 ${UNPACKDIR}/rspamd.sh ${D}/etc/init.d/rspamd

    LOCAL_CONFDIR="${D}/etc/rspamd/local.d"
    install -Dm644 ${UNPACKDIR}/classifier-bayes.conf -t "$LOCAL_CONFDIR"
    install -Dm644 ${UNPACKDIR}/redis.conf -t "$LOCAL_CONFDIR"
    install -Dm644 ${UNPACKDIR}/actions.conf -t "$LOCAL_CONFDIR"
    install -Dm644 ${UNPACKDIR}/milter_headers.conf -t "$LOCAL_CONFDIR"
}

pkg_postinst:${PN}() {
    chown rspamd:rspamd $D/var/lib/rspamd
}

FILES:${PN} += " \
    /var/lib/rspamd \
    /etc/init.d/rspamd \
"

INITSCRIPT_NAME = "rspamd"
