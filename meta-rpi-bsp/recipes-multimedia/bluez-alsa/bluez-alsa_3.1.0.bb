
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=72d868d66bdd5bf51fe67734431de057"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "\
git://github.com/Arkq/bluez-alsa.git \
file://bluealsa \
"

PV = "3.1.0"
SRCREV = "b09f373ea7dbc6e3ecbcb74d7299f5230cdc6e59"

S = "${WORKDIR}/git"

# NOTE: the following prog dependencies are unknown, ignoring: rst2man lcov
#       genhtml rst2man.py
# NOTE: unable to map the following pkg-config dependencies: glib-2.0 libmpg123
#       openaptx bash-completion ncurses ldacBT-enc ldacBT-dec bluez sbc check
#       libopenaptx fdk-aac libunwind libbsd gio-unix-2.0 ldacBT-abr openaptxhd
#       alsa
#       (this is based on recipes that have previously been built and packaged)
# NOTE: the following library dependencies are unknown, ignoring: mp3lame
#       (this is based on recipes that have previously been built and packaged)
DEPENDS = "dbus alsa-lib bluez5 glib-2.0 libbsd sbc"

# libasound, libbluetooth, libbsd, libglib2.0, libsbc
RDEPENDS_${PN}_append = " alsa-lib bluez5 glib-2.0 libbsd sbc dbus-lib"

FILES_${PN}_append = " \
${libdir}/alsa-lib/libasound_module_ctl_bluealsa.so \
${libdir}/alsa-lib/libasound_module_pcm_bluealsa.so \
"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "bluealsa"

inherit pkgconfig autotools update-rc.d

# Extra ./configure options
EXTRA_OECONF = ""

do_install_append() {
    install -d ${D}${INIT_D_DIR}
    install -m755 ${WORKDIR}/bluealsa ${D}${INIT_D_DIR}/bluealsa
}
