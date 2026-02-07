# "virtual" entries only work in PROVIDES and PREFERRED_PROVIDER, not in the
# runtime version of the variable.
# https://lists.yoctoproject.org/g/yocto/message/57836
RPROVIDES:${PN}:remove = "virtual-rauc-conf"
