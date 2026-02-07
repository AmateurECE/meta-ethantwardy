SUMMARY = "Manipulate DNS records on various DNS providers in a standardized/agnostic way"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=bc5dc1e358941b563b5e53b9f0d53bc8"

SRC_URI[sha256sum] = "3c40174e9d657289d4d4f81d44451c4d63f8c26c8513e4d7a61c30ac456aab8f"

inherit pypi python_hatchling

PYPI_PACKAGE = "dns_lexicon"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

RDEPENDS:${PN} += " \
    python3-beautifulsoup4 \
    python3-cryptography \
    python3-dnspython \
    python3-pyotp \
    python3-pyyaml \
    python3-requests \
    python3-tldextract \
"

BBCLASSEXTEND = "native"
