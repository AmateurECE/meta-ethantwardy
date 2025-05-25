SUMMARY = "A string processing language for creating stemming algorithms"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

include rspamd.inc

SRCREV = "${AUTOREV}"

OECMAKE_TARGET_COMPILE = "contrib/snowball/snowball"

do_install() {
	bbnote DESTDIR='${D}' ${CMAKE_VERBOSE} cmake --install '${B}/contrib/snowball'
	eval DESTDIR='${D}' ${CMAKE_VERBOSE} cmake --install '${B}/contrib/snowball'
}

BBCLASSEXTEND = "native nativesdk"
