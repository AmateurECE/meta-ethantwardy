SUMMARY = "LuaDNS Authenticator plugin for Certbot"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "b5147c8ab8d2daf2b33e45d973f1422e4d64fe8ae1a664e78d861d861028d35a"

inherit pypi setuptools3

PYPI_PACKAGE = "certbot_dns_luadns"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    certbot \
    python3-acme \
    python3-dns-lexicon \
"

BBCLASSEXTEND = "native"
