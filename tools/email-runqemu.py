import argparse
import configparser
import os
import socket
from os import path

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

def someone_is_listening_on(address):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as sock:
        try:
            sock.bind(address)
            return False
        except Exception as e:
            return True

def efi_options(efivars_file: str):
    return [
        "-drive", "if=pflash,format=raw,readonly=on,file=/usr/share/OVMF/OVMF_CODE_4M.fd",
        "-drive", f"if=pflash,format=raw,file={efivars_file}",
    ]

def data_drive_options(data_file: str):
    return [
        "-drive", f"if=none,id=hdata,file={data_file},format=raw",
        "-device", "scsi-hd,drive=hdata"
    ]

def rootfs_options(config_directory, image):
    if not image:
        deploydir = "build/tmp/deploy/images/qemu-mutable-gadget"
        rootfs = f"{deploydir}/email-gadget-image-qemu-mutable-gadget.rootfs.wic"
    else:
        rootfs = f"{config_directory}/{image}"
    return [
        "-drive", f"if=none,id=hd,file={rootfs},format=raw,readonly=on",
        "-device", "scsi-hd,drive=hd"
    ]

def networking_options(wan_mac: str, lan_mac: str):
    return [
        # User interface for accessing the internet
        "-device", f"virtio-net-pci,netdev=net0,mac={wan_mac}",
        "-netdev", "user,id=net0",
        # Guest network
        "-device", f"virtio-net-pci,netdev=net1,mac={lan_mac}",
        "-netdev", f"socket,id=net1,mcast=230.0.0.1:1234",
    ]

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-f','--file', required=True)
    parser.add_argument('config')
    args = parser.parse_args()

    config = configparser.ConfigParser()
    config.read(args.file)
    config_directory = path.dirname(args.file)

    efivars_file = config[args.config]['efivars_file']
    data_file = config[args.config]['data_file']
    wan_mac = config[args.config]['wan_mac']
    lan_mac = config[args.config]['lan_mac']
    image = config[args.config]['image'] if 'image' in config[args.config] else None

    executable = '/usr/bin/qemu-system-x86_64'
    options = MACHINE_OPTIONS  + URANDOM_OPTIONS \
            + efi_options(efivars_file) \
            + rootfs_options(config_directory, image) \
            + data_drive_options(data_file) \
            + networking_options(wan_mac, lan_mac) + SERIAL_OPTIONS
    os.execvp(executable, [executable] + options)

if __name__ == '__main__':
    main()
