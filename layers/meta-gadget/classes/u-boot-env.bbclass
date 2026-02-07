DEPENDS = "u-boot-tools-native"
do_compile[depends] += "u-boot:do_deploy"

# Set an environment variable in the environment
set_envvar() {
    sed -i -e "/$1/d" ${B}/u-boot-env.tmp
    printf "$1=$2\n" >> ${B}/u-boot-env.tmp
}

# Set an environment variable if $2 is non-empty
set_envvar_if_nonempty() {
    if [ -n "$2" ]; then
        set_envvar $1 $2
    fi
}

do_compile() {
    cp ${DEPLOY_DIR_IMAGE}/u-boot-initial-env ${B}/u-boot-env.tmp

    do_configure_env

    cat ${B}/u-boot-env.tmp | sort > ${B}/u-boot-env.txt
    # TODO: It would be pretty cool not to hard-code this value.
    mkenvimage -s 0x4000 -o ${B}/uboot.env ${B}/u-boot-env.txt
}

do_deploy() {
    install -Dm0644 ${B}/uboot.env -t ${DEPLOYDIR}
}
addtask deploy after do_compile

PROVIDES += "virtual/u-boot-env"

inherit deploy
