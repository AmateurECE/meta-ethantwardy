FILESEXTRAPATHS:prepend := "${THISDIR}/patches:"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=d91b3e8512398dd9589241eb5c509e5c \
                    file://GPL;md5=ebf4e8b49780ab187d51bd26aaa022c6"

SRC_URI = " \
    http://ftp.mutt.org/pub/mutt/mutt-${PV}.tar.gz \
    file://0001-build-Generate-empty-build-information.patch \
"
SRC_URI[sha256sum] = "043af312f64b8e56f7fd0bf77f84a205d4c498030bd9586457665c47bb18ce38"

DEPENDS = "openssl ncurses"

RDEPENDS:${PN} += " perl"

# NOTE: if this software is not capable of being built in a separate build directory
# from the source, you should replace autotools with autotools-brokensep in the
# inherit line
inherit texinfo gettext autotools

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = " \
    --enable-imap \
    --with-homespool=Maildir \
    --without-gnutls --with-ssl \
"
# TODO: --with-gsasl --without-sasl
