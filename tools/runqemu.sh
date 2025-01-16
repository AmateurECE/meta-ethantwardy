#!/bin/sh
DEPLOY=build/tmp/deploy/images/qemux86-64

CONSOLE_SOCK=${XDG_RUNTIME_DIR}/qemu-console.sock

qemu-system-x86_64 \
	-machine pc \
	-m 1024 \
	-kernel ${DEPLOY}/bzImage \
	-initrd ${DEPLOY}/core-image-minimal-initramfs-qemux86-64.cpio.gz \
	-monitor stdio \
	-chardev socket,id=serial0,path=${CONSOLE_SOCK},server=on \
	-serial chardev:serial0 \
	-nographic \
	-vga none \
	-append 'console=ttyS0,115200n8 init=/bin/sh'
