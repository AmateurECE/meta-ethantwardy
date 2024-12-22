DESCRIPTION = "Creates a u-boot environment that configures U-boot for netbooting"
LICENSE = "MIT"

DEPENDS = "u-boot-tools-native"
do_compile[depends] += "u-boot:do_deploy"

B = "${WORKDIR}/build"

set_envvar() {
    sed -i -e "/$1/d" ${B}/u-boot-env.tmp
    printf "$1=$2\n" >> ${B}/u-boot-env.tmp
}

do_compile() {
    cp ${DEPLOY_DIR_IMAGE}/u-boot-initial-env ${B}/u-boot-env.tmp

    set_envvar boot_targets pxe

    # FIXME: Does not handle port numbers
    if [ -n "${NETBOOT_SERVER_IP}" ]; then
        set_envvar serverip ${NETBOOT_SERVER_IP}
    fi

    cat ${B}/u-boot-env.tmp | sort > ${B}/u-boot-env.txt
    mkenvimage -s 0x4000 -o ${B}/uboot.env ${B}/u-boot-env.txt
}

do_deploy() {
    install -Dm0644 ${B}/uboot.env -t ${DEPLOYDIR}
}
addtask deploy after do_compile

IMAGE_BOOT_FILES = "uboot.env"

PROVIDES += "virtual/u-boot-env"

inherit deploy
