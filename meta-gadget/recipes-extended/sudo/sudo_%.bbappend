# Allow users of group `sudo` to execute all commands using sudo.
do_install:append() {
    sed -i -E -e 's/^# (%sudo\s+ALL=\(ALL:ALL\) ALL)/\1/' ${D}/etc/sudoers
}
