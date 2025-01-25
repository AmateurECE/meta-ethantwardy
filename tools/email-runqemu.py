import argparse
import configparser
import os

SERIAL_OPTIONS=[
	"-serial", "mon:stdio",
	"-serial", "null",
	"-nographic"
]

URANDOM_OPTIONS=[
	"-object", "rng-random,filename=/dev/urandom,id=rng0",
	"-device", "virtio-rng-pci,rng=rng0",
]

MACHINE_OPTIONS=[
    "-cpu", "IvyBridge",
    "-machine", "q35,i8042=off",
    "-smp", "4",
    "-m", "256",
    "-device", "virtio-scsi-pci,id=scsi",
]

def data_drive_options(data_file: str):
    return [
        "-drive", f"if=none,id=hdata,file={data_file},format=raw",
        "-device", "scsi-hd,drive=hdata"
    ]

def rootfs_options():
    deploydir="build/tmp-glibc/deploy/images/qemux86-64"
    rootfs=f"{deploydir}/qemu-email-gadget-qemux86-64.rootfs.wic"
    return [
        "-drive", f"if=none,id=hd,file={rootfs},format=raw,readonly=on",
        "-device", "scsi-hd,drive=hd"
    ]

def networking_options(mac_address: str):
    return [
        "-device", f"virtio-net-pci,netdev=net0,mac={mac_address}",
        "-netdev", "user,id=net0",
    ]

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-f','--file', required=True)
    parser.add_argument('config')
    args = parser.parse_args()

    config = configparser.ConfigParser()
    config.read(args.file)

    data_file = config[args.config]['data_file']
    mac_address = config[args.config]['mac_address']

    executable = '/usr/bin/qemu-system-x86_64'
    options = MACHINE_OPTIONS  + URANDOM_OPTIONS + rootfs_options() \
            + data_drive_options(data_file) \
            + networking_options(mac_address) + SERIAL_OPTIONS
    os.execvp(executable, [executable] + options)

if __name__ == '__main__':
    main()
