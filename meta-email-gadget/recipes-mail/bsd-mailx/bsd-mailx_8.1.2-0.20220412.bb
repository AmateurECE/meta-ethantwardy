LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM ?= "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "https://deb.debian.org/debian/pool/main/b/bsd-mailx/bsd-mailx_${PV}cvs.orig.tar.xz"
SRC_URI[sha256sum] = "9ef5ba71c8bf82c528389ca46e11296fb110e4b30eaf33f5888a22b957c5d640"
SRC_URI += " \
    file://00-Makefiles.patch \
    file://01-Fix-includes.patch \
    file://02-Base-fixes-1.patch \
    file://03-Base-fixes-2.patch \
    file://04-Add-custom-header.patch \
    file://05-Mailx-fixes.patch \
    file://06-Use-lockf-instead-of-flock.patch \
    file://07-Initialize-head-struct.patch \
    file://08-Use-liblockfile-library.patch \
    file://09-Saved-mbox-message.patch \
    file://10-Reply-To-header.patch \
    file://11-Showname-option.patch \
    file://12-REPLYTO-can-be-set-in-.mailrc-too.patch \
    file://13-Mailx-concatenates-messages.patch \
    file://14-Truncate-mailbox-instead-of-deleting-it.patch \
    file://15-No-space-left-in-tmp.patch \
    file://16-Stdin-not-a-tty.patch \
    file://18-Wait-for-sendmail.patch \
    file://19-Fix-compilation-on-Hurd.patch \
    file://20-Don-t-delete-temporary-file.patch \
    file://21-Use-wordexpr-instead-of-echo.patch \
    file://22-Replace-newlines-with-spaces.patch \
    file://24-False-cant-send-email-errors.patch \
    file://25-Fix-confusing-error.patch \
    file://26-Add-missing-include.patch \
    file://27-Use-FOPEN_MAX.patch \
    file://28-Fix-gcc-warning.patch \
    file://29-Document-two-dashes-separator.patch \
    file://30-Add-missing-includes.patch \
    file://31-Do-not-call-pledge.patch \
    file://32-Fix-FTBFS-on-Hurd.patch \
    file://33-Add-MIME-headers.patch \
    file://34-Fix-strnvis.patch \
    file://35-Fix-new-warnings-and-error.patch \
"

S = "${WORKDIR}/bsd-mailx-${PV}cvs.orig"

DEPENDS = " \
    libbsd \
    liblockfile \
"

FILES:${PN} += " \
    /usr/share/bsd-mailx \
    /usr/share/bsd-mailx/mail.tildehelp \
    /usr/share/bsd-mailx/mail.help \
"

do_compile () {
    oe_runmake
}

do_install () {
    install -d ${D}/usr/bin
    install -d ${D}/usr/share/bsd-mailx
    install -d ${D}/usr/share/man/man1
    install -d ${D}/etc
    oe_runmake install DESTDIR=${D}
    ln -s /usr/bin/bsd-mailx ${D}/usr/bin/mailx
}

