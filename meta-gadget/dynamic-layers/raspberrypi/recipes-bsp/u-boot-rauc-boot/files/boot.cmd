test -n "${BOOT_ORDER}" || setenv BOOT_ORDER "system0 system1"
test -n "${BOOT_system0_LEFT}" || setenv BOOT_system0_LEFT 3
test -n "${BOOT_system1_LEFT}" || setenv BOOT_system1_LEFT 3

saveenv

setenv bootpart
setenv bootslot
for BOOT_SLOT in "${BOOT_ORDER}"; do
  if test "x${bootpart}" != "x"; then
    # Skip the remaining iterations
  elif test "x${BOOT_SLOT}" = "xsystem0"; then
    if test 0x${BOOT_system0_LEFT} -gt 0; then
      echo "Found valid slot system0, ${BOOT_system0_LEFT} attempts remaining"
      setexpr BOOT_system0_LEFT ${BOOT_system0_LEFT} - 1
      setenv bootpart 2
      setenv bootslot "system0"
    fi
  elif test "x${BOOT_SLOT}" = "xsystem1"; then
    if test 0x${BOOT_system1_LEFT} -gt 0; then
      echo "Found valid slot system1, ${BOOT_system1_LEFT} attempts remaining"
      setexpr BOOT_system1_LEFT ${BOOT_system1_LEFT} - 1
      setenv bootpart 3
      setenv bootslot "system1"
    fi
  fi
done

ext4load mmc 0:${bootpart} ${kernel_addr_r} /boot/Image
fdt addr ${fdt_addr} && fdt get value bootargs /chosen bootargs

# The RPi firmware sets up a kernel command line when it's fixing up the device
# tree. Unfortunately, this includes some basic rootfs selection.
setexpr bootargs gsub "root=/dev/mmcblk0p2" "" "${bootargs}"
setexpr bootargs gsub "rootwait" "" "${bootargs}"
setexpr bootargs gsub "rootfstype=ext4" "" "${bootargs}"
setexpr bootargs gsub "console=\\S+" "" "${bootargs}"
setexpr bootargs gsub "kgdboc=\\S+" "" "${bootargs}"

setenv bootargs "${bootargs} console=ttyS0,115200 rdinit=/init rauc.slot=${bootslot}"
booti ${kernel_addr_r} - ${fdt_addr}
