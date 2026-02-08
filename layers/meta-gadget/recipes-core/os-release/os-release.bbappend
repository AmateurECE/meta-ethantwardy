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

BUILD_ID := "${@get_git_version(d)}"
BUILD_ID[vardepvalue] = "${BUILD_ID}"
do_compile[vardeps] += "BUILD_ID"

BB_DONT_CACHE = "1"

OS_RELEASE_FIELDS += "BUILD_ID"
