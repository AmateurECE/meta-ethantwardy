FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:ab-mutable = " file://mutable.fstab"

def get_git_version(d):
    import subprocess
    import os

    layer_dir = d.getVar('THISDIR') or ''
    try:
        version = subprocess.check_output(
            ['git', 'describe', '--always', '--dirty'],
            cwd=layer_dir,
            stderr=subprocess.DEVNULL
        ).decode('utf-8').strip()
    except Exception:
        version = "unknown"
    return version

GIT_DESCRIBE_VERSION := "${@get_git_version(d)}"
GIT_DESCRIBE_VERSION[vardepvalue] = "${GIT_DESCRIBE_VERSION}"

BB_DONT_CACHE = "1"

do_compile:append() {
    cat > os-release <<EOF
NAME="${DISTRO_NAME}"
VERSION=${DISTRO_VERSION}
VERSION_ID=${GIT_DESCRIBE_VERSION}
ID=${DISTRO}
PRETTY_NAME="${DISTRO_NAME} ${DISTRO_VERSION}"
EOF
}
do_compile[vardeps] += "GIT_DESCRIBE_VERSION"

do_install:append() {
    install -Dm0644 os-release -t ${D}${sysconfdir}
}

do_install:append:ab-mutable() {
    install -Dm644 ${UNPACKDIR}/mutable.fstab ${D}/etc/fstab
    install -d ${D}/data
}

FILES:append:ab-mutable:${PN} = " /data"
