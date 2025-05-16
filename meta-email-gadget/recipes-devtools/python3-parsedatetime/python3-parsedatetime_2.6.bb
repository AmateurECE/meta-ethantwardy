SUMMARY = "Parse human-readable date/time text."
HOMEPAGE = "https://github.com/bear/parsedatetime/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=34a845f28f361454a6913882b4d3bb53"

SRC_URI[sha256sum] = "4cb368fbb18a0b7231f4d76119165451c8d2e35951455dfee97c62a87b04d455"

inherit pypi setuptools3

PYPI_PACKAGE = "parsedatetime"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

BBCLASSEXTEND = "native"
