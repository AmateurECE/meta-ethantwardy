SUMMARY = "Format dates according to RFC3339"
HOMEPAGE = "http://pypi.python.org/pypi/rfc3339/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d6df9397fb02a3af16cc225b8371d80c"

SRC_URI[sha256sum] = "c569a9714faf115cdb20b51e830e798c1f4de8dabb07f6ff25d221b5d09d8d7f"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "pyrfc3339"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

DEPENDS = " \
    python3-setuptools-scm-native \
"

BBCLASSEXTEND = "native"
