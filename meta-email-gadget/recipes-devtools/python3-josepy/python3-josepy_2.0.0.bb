SUMMARY = "JOSE protocol implementation in Python"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "e7d7acd2fe77435cda76092abe4950bb47b597243a8fb733088615fa6de9ec40"

inherit pypi python_poetry_core

PYPI_PACKAGE = "josepy"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    python3-cryptography \
"

BBCLASSEXTEND = "native"
