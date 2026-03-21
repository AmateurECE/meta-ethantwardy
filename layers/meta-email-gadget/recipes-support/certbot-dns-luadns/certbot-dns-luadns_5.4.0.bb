SUMMARY = "LuaDNS Authenticator plugin for Certbot"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "7edcf171a34f9156e1c1c3e2cf59d2172d93c57746b226023addd05f49af4221"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "certbot_dns_luadns"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    certbot \
"

BBCLASSEXTEND = "native"
