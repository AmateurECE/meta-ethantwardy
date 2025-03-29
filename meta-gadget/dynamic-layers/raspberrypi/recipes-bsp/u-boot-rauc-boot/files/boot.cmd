test -n "${BOOT_ORDER}" || setenv BOOT_ORDER "A B"
test -n "${BOOT_A_LEFT}" || setenv BOOT_A_LEFT 3
test -n "${BOOT_B_LEFT}" || setenv BOOT_B_LEFT 3

saveenv

setenv bootpart
for BOOT_SLOT in "${BOOT_ORDER}"; do
  if test "x${bootpart}" != "x"; then
    # Skip the remaining iterations
  elif test "x${BOOT_SLOT}" = "xA"; then
    if test 0x${BOOT_A_LEFT} -gt 0; then
      echo "Found valid slot A, ${BOOT_A_LEFT} attempts remaining"
      setexpr BOOT_A_LEFT ${BOOT_A_LEFT} - 1
      setenv bootpart 2
    fi
  elif test "x${BOOT_SLOT}" = "xB"; then
    if test 0x${BOOT_B_LEFT} -gt 0; then
      echo "Found valid slot B, ${BOOT_B_LEFT} attempts remaining"
      setexpr BOOT_B_LEFT ${BOOT_B_LEFT} - 1
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
