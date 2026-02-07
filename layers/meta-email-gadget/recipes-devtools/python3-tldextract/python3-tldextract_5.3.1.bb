SUMMARY = "Accurately separates a URL's subdomain, domain, and public suffix, using the Public Suffix List (PSL)."
HOMEPAGE = "https://github.com/john-kurkowski/tldextract"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0c87f552b1a2bb24e6955f5f56249cf1"

SRC_URI[sha256sum] = "a72756ca170b2510315076383ea2993478f7da6f897eef1f4a5400735d5057fb"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "tldextract"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

DEPENDS += " \
    python3-setuptools-scm-native \
"

RDEPENDS:${PN} += " \
    python3-filelock \
    python3-idna \
    python3-requests \
    python3-requests-file \
"

BBCLASSEXTEND = "native"
