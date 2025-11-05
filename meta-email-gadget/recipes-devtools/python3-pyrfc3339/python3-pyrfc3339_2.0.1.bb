SUMMARY = "Format dates according to RFC3339"
HOMEPAGE = "http://pypi.python.org/pypi/rfc3339/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b234c8fb351636a5b91a38e882cd5428"

SRC_URI[sha256sum] = "e47843379ea35c1296c3b6c67a948a1a490ae0584edfcbdea0eaffb5dd29960b"

inherit pypi python_poetry_core

PYPI_PACKAGE = "pyrfc3339"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

BBCLASSEXTEND = "native"
