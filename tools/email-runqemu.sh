#!/bin/sh

usage() {
	printf >&2 "Usage $0 -f <email-data-drive-image>\n"
	exit 1
}

while getopts ":f:" opt; do
	case "${opt}" in
	f)
		DATA_DRIVE_PATH=$(realpath ${OPTARG})
		;;
	*)
		usage
		;;
	esac
done

if [ -z "${DATA_DRIVE_PATH}" ]; then
	usage
fi

if [ ! -f kas/email-qemu.yaml ]; then
	printf >&2 '%s\n' "Error: Run this in a directory where " \
		"kas/email-qemu.yaml can be found"
	exit 1
fi

DATA_DRIVE_ARGUMENTS="\
	-drive if=none,id=hdata,file=${DATA_DRIVE_PATH},format=raw \
	-device scsi-hd,drive=hdata"
ROOTFS=tmp-glibc/deploy/images/qemux86-64/qemu-email-gadget-qemux86-64.rootfs.wic
RUNQEMU_ARGUMENTS="\
	${ROOTFS} \
	wic \
	serial \
	nographic \
	slirp \
	qemuparams=\"${DATA_DRIVE_ARGUMENTS}\""

kas shell -c "runqemu ${RUNQEMU_ARGUMENTS}" kas/email-qemu.yaml
