# Auto-Generated by cargo-bitbake 0.3.15
#
inherit cargo

# If this is git based prefer versioned ones if they exist
# DEFAULT_PREFERENCE = "-1"

# how to get bluez-player could be as easy as but default to a git checkout:
# SRC_URI += "crate://crates.io/bluez-player/0.1.0"
SRC_URI += "git://github.com/AmateurECE/bluez-player;protocol=https;nobranch=1"
SRCREV = "ade44a35533423b9abe818b6b377bdd7034121ea"
S = "${WORKDIR}/git"
CARGO_SRC_DIR = ""
PV_append = ".AUTOINC+ade44a3553"

# please note if you have entries that do not begin with crate://
# you must change them to how that package can be fetched
SRC_URI += " \
    crate://crates.io/autocfg/1.0.1 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/bytes/1.1.0 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/dbus-crossroads/0.4.0 \
    crate://crates.io/dbus-tokio/0.7.5 \
    crate://crates.io/dbus/0.9.4 \
    crate://crates.io/futures-channel/0.3.17 \
    crate://crates.io/futures-core/0.3.17 \
    crate://crates.io/futures-executor/0.3.17 \
    crate://crates.io/futures-io/0.3.17 \
    crate://crates.io/futures-macro/0.3.17 \
    crate://crates.io/futures-sink/0.3.17 \
    crate://crates.io/futures-task/0.3.17 \
    crate://crates.io/futures-util/0.3.17 \
    crate://crates.io/futures/0.3.17 \
    crate://crates.io/hermit-abi/0.1.19 \
    crate://crates.io/instant/0.1.11 \
    crate://crates.io/libc/0.2.102 \
    crate://crates.io/libdbus-sys/0.2.2 \
    crate://crates.io/lock_api/0.4.5 \
    crate://crates.io/log/0.4.14 \
    crate://crates.io/memchr/2.4.1 \
    crate://crates.io/mio/0.7.13 \
    crate://crates.io/miow/0.3.7 \
    crate://crates.io/ntapi/0.3.6 \
    crate://crates.io/num_cpus/1.13.0 \
    crate://crates.io/once_cell/1.8.0 \
    crate://crates.io/parking_lot/0.11.2 \
    crate://crates.io/parking_lot_core/0.8.5 \
    crate://crates.io/pin-project-lite/0.2.7 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/pkg-config/0.3.20 \
    crate://crates.io/proc-macro-hack/0.5.19 \
    crate://crates.io/proc-macro-nested/0.1.7 \
    crate://crates.io/proc-macro2/1.0.29 \
    crate://crates.io/quote/1.0.9 \
    crate://crates.io/redox_syscall/0.2.10 \
    crate://crates.io/scopeguard/1.1.0 \
    crate://crates.io/signal-hook-registry/1.4.0 \
    crate://crates.io/slab/0.4.4 \
    crate://crates.io/smallvec/1.6.1 \
    crate://crates.io/syn/1.0.77 \
    crate://crates.io/tokio-macros/1.3.0 \
    crate://crates.io/tokio/1.8.3 \
    crate://crates.io/unicode-xid/0.2.2 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
"



# FIXME: update generateme with the real MD5 of the license file
LIC_FILES_CHKSUM = " \
    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
"

SUMMARY = "Stream audio from incoming Bluetooth connections to the system PCM device."
HOMEPAGE = "https://github.com/AmateurECE/bluez-player"
LICENSE = "MIT"

# includes this file if it exists but does not fail
# this is useful for anything you may want to override from
# what cargo-bitbake generates.
include bluez-player-${PV}.inc
include bluez-player.inc
