# We don't use cmdline.txt, so don't install it in the bootimg-partition
DEPENDS:remove = " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'minimal-bootpart', 'rpi-cmdline', '', d)} \
"

# There's also a task dependency.
python __anonymous() {
    if 'minimal-bootpart' in d.getVar('MACHINE_FEATURES', True):
        do_deploy = d.getVarFlag('do_deploy', 'depends', True)
        d.setVarFlag('do_deploy', 'depends',
                     do_deploy.replace('rpi-cmdline:do_deploy', ''))
}
