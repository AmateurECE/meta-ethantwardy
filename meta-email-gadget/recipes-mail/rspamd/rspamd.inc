SRC_URI = " \
    git://github.com/rspamd/rspamd.git;protocol=https;branch=master \
    file://0001-backward-cpp-Enable-for-libdwarf-0.patch \
    file://0002-Don-t-add-_INCLUDEDIR-variables-to-include_directori.patch \
    file://0003-snowball-Allow-cross-compilation.patch \
"

# TODO: We could use luajit instead of lua here if we -DENABLE_LUAJIT=ON
DEPENDS += " \
    binutils \
    elfutils \
    glib-2.0 \
    icu \
    libarchive \
    libdwarf-native \
    libmd \
    libpcre2 \
    libsodium \
    lua \
    openssl \
    ragel-native \
    sqlite3 \
"

# TODO: All of these options are hacks.
EXTRA_OECMAKE = " \
    -D_CAN_RUN=0 \
    -DHAVE_ATOMIC_BUILTINS_EXITCODE=0 \
    -DHAVE_BUILTIN_CPU_SUPPORTS_EXITCODE=0 \
    -DHAVE_RDTSC_EXITCODE=0 \
    -DENABLE_LUAJIT=OFF \
"

inherit cmake-qemu perlnative pkgconfig
