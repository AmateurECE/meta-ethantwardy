SUMMARY = "Accurately separates a URL's subdomain, domain, and public suffix, using the Public Suffix List (PSL)."
HOMEPAGE = "https://github.com/john-kurkowski/tldextract"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0c87f552b1a2bb24e6955f5f56249cf1"

SRC_URI[sha256sum] = "b3d2b70a1594a0ecfa6967d57251527d58e00bb5a91a74387baa0d87a0678609"

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
