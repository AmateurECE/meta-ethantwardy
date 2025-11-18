SUMMARY = "LuaDNS Authenticator plugin for Certbot"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "396824219cfc9fdb20d94b9f86a08e4386cc7fa6a064902a5ac4def82f6aef39"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "certbot_dns_luadns"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    certbot \
    python3-acme \
    python3-dns-lexicon \
"

BBCLASSEXTEND = "native"
