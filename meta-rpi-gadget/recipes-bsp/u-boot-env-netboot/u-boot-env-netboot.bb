DESCRIPTION = "Creates a u-boot environment that configures U-boot for netbooting"
LICENSE = "MIT"

DEPENDS = "u-boot-tools-native"
do_compile[depends] += "u-boot:do_deploy"

B = "${WORKDIR}/build"

python () {
    host = d.getVar('NETBOOT_TFTP_HOST')
    if not host:
        return

    if ':' in host:
        parts = host.split(':')
        d.setVar('NETBOOT_TFTP_IP', parts[0])
        d.setVar('NETBOOT_TFTP_PORT', parts[1])
    else:
        d.setVar('NETBOOT_TFTP_IP', host)
}

set_envvar() {
    sed -i -e "/$1/d" ${B}/u-boot-env.tmp
    printf "$1=$2\n" >> ${B}/u-boot-env.tmp
}

set_envvar_if_nonempty() {
    if [ -n "$2" ]; then
        set_envvar $1 $2
    fi
}

do_compile() {
    cp ${DEPLOY_DIR_IMAGE}/u-boot-initial-env ${B}/u-boot-env.tmp

    set_envvar boot_targets pxe
    set_envvar_if_nonempty serverip ${NETBOOT_TFTP_IP}
    set_envvar_if_nonempty tftpdstp ${NETBOOT_TFTP_PORT}

    cat ${B}/u-boot-env.tmp | sort > ${B}/u-boot-env.txt
    mkenvimage -s 0x4000 -o ${B}/uboot.env ${B}/u-boot-env.txt
}

do_deploy() {
    install -Dm0644 ${B}/uboot.env -t ${DEPLOYDIR}
}
addtask deploy after do_compile

PROVIDES += "virtual/u-boot-env"

inherit deploy
