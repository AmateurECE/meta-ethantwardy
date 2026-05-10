SUMMARY = "Certbot: Automatically enable HTTPS on your website"
HOMEPAGE = "https://certbot.eff.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=16542115f15152bdc6d80c5b5d208e70"

SRC_URI[sha256sum] = "c72c813c4c8646a9658bc7a9530c757c76067503fa2f5d7eb12027e8bbf798d7"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "certbot"
SRC_URI = "https://files.pythonhosted.org/packages/source/${PYPI_PACKAGE[0]}/${PYPI_PACKAGE}/${PYPI_PACKAGE}-${PV}.tar.gz"

# NOTE: The versions of these dependencies are specified in the source at
# ./tools/requirements.txt
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
