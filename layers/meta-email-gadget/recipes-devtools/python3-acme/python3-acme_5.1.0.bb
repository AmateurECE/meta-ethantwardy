SUMMARY = "ACME protocol implementation in Python"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "7b97820857d9baffed98bca50ab82bb6a636e447865d7a013a7bdd7972f03cda"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "acme"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    python3-cryptography \
    python3-josepy \
    python3-pyopenssl \
    python3-pyrfc3339 \
    python3-pytz \
    python3-requests \
"

BBCLASSEXTEND = "native"
