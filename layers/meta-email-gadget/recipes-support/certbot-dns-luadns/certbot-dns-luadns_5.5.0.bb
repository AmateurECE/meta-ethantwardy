SUMMARY = "LuaDNS Authenticator plugin for Certbot"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d2c2a5517cd7fd190a1aa6dfa23abb7a"

SRC_URI[sha256sum] = "90975f33c6a9a224d2d60497866c17a18e41aa68dc443f47144c79203f6d9ba6"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "certbot_dns_luadns"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    certbot \
    python3-acme \
    python3-dns-lexicon \
"

BBCLASSEXTEND = "native"
