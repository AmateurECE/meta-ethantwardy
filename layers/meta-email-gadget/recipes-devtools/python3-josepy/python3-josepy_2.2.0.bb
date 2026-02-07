SUMMARY = "JOSE protocol implementation in Python"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "74c033151337c854f83efe5305a291686cef723b4b970c43cfe7270cf4a677a9"

inherit pypi python_poetry_core

PYPI_PACKAGE = "josepy"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    python3-cryptography \
"

BBCLASSEXTEND = "native"
