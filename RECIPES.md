# Net-booting Raspberrypi4-64

After booting to U-boot:

```
U-boot>setenv serverip 192.168.1.60
U-boot>tftp ${kernel_addr_r} Image
U-boot>tftp ${fdt_addr_r} bcm2711-rpi-4-b.dtb
U-boot>booti ${kernel_addr_r} - ${fdt_addr_r}
```
