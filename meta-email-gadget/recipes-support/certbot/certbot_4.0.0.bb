SUMMARY = "Certbot: Automatically enable HTTPS on your website"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=16542115f15152bdc6d80c5b5d208e70"

SRC_URI[sha256sum] = "a867bfbb5126516c12d4c8a93909ef1e4d5309fc4e9f5b97b2d987b0ffd4bbe3"

inherit pypi setuptools3

PYPI_PACKAGE = "certbot"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    python3-acme \
    python3-configargparse \
    python3-configobj \
    python3-cryptography \
    python3-distro \
    python3-josepy \
    python3-logging \
    python3-parsedatetime \
    python3-pyrfc3339 \
    python3-pytz \
"

BBCLASSEXTEND = "native"
