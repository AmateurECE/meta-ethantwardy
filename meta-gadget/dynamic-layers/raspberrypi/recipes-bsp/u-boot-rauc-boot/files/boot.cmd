test -n "${BOOT_ORDER}" || setenv BOOT_ORDER "system0 system1"
test -n "${BOOT_system0_LEFT}" || setenv BOOT_system0_LEFT 3
test -n "${BOOT_system1_LEFT}" || setenv BOOT_system1_LEFT 3

saveenv

setenv bootpart
for BOOT_SLOT in "${BOOT_ORDER}"; do
  if test "x${bootpart}" != "x"; then
    # Skip the remaining iterations
  elif test "x${BOOT_SLOT}" = "xsystem0"; then
    if test 0x${BOOT_system0_LEFT} -gt 0; then
      echo "Found valid slot system0, ${BOOT_system0_LEFT} attempts remaining"
      setexpr BOOT_system0_LEFT ${BOOT_system0_LEFT} - 1
      setenv bootpart 2
    fi
  elif test "x${BOOT_SLOT}" = "xsystem1"; then
    if test 0x${BOOT_system1_LEFT} -gt 0; then
      echo "Found valid slot system1, ${BOOT_system1_LEFT} attempts remaining"
      setexpr BOOT_system1_LEFT ${BOOT_system1_LEFT} - 1
      setenv bootpart 3
    fi
  fi
done

ext4load mmc 0:${bootpart} ${kernel_addr_r} /boot/Image
ext4load mmc 0:${bootpart} ${fdt_addr_r} /boot/bcm2711-rpi-4-b.dtb
fdt addr ${fdt_addr_r}
fdt get value bootargs /chosen bootargs
setenv bootargs "${bootargs} root=/dev/mmcblk0p${bootpart} rootwait"
booti ${kernel_addr_r} - ${fdt_addr_r}
