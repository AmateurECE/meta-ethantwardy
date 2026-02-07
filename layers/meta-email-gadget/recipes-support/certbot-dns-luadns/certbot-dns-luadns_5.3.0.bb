SUMMARY = "LuaDNS Authenticator plugin for Certbot"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "c4110645d1a8c2d5a0b68b1b06473ef2ea70aa53da1002d0e8374c185f843564"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "certbot_dns_luadns"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    certbot \
    python3-acme \
    python3-dns-lexicon \
"

BBCLASSEXTEND = "native"
