import argparse
import configparser
import os
import socket

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

def data_drive_options(data_file: str):
    return [
        "-drive", f"if=none,id=hdata,file={data_file},format=raw",
        "-device", "scsi-hd,drive=hdata"
    ]

def rootfs_options(image):
    deploydir = "build/tmp/deploy/images/qemux86-64"
    if not image:
        image = f"{deploydir}/qemu-email-gadget-qemux86-64.rootfs.wic"
    else:
        image = f"{deploydir}/{image}"
    return [
        "-drive", f"if=none,id=hd,file={image},format=raw,readonly=on",
        "-device", "scsi-hd,drive=hd"
    ]

def networking_options(wan_mac: str, lan_mac: str):
    address = ('0.0.0.0', 1234)
    _, port = address
    if someone_is_listening_on(address):
        lan_spec = f"connect=127.0.0.1:{port}"
    else:
        lan_spec = f"listen=:{port}"
    return [
        # User interface for accessing the internet
        "-device", f"virtio-net-pci,netdev=net0,mac={wan_mac}",
        "-netdev", "user,id=net0",
        # Guest network
        "-device", f"virtio-net-pci,netdev=net1,mac={lan_mac}",
        "-netdev", f"socket,id=net1,{lan_spec}",
    ]

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('-f','--file', required=True)
    parser.add_argument('config')
    args = parser.parse_args()

    config = configparser.ConfigParser()
    config.read(args.file)

    data_file = config[args.config]['data_file']
    wan_mac = config[args.config]['wan_mac']
    lan_mac = config[args.config]['lan_mac']
    image = config[args.config]['image'] if 'image' in config[args.config] else None

    executable = '/usr/bin/qemu-system-x86_64'
    options = MACHINE_OPTIONS  + URANDOM_OPTIONS + rootfs_options(image) \
            + data_drive_options(data_file) \
            + networking_options(wan_mac, lan_mac) + SERIAL_OPTIONS
    os.execvp(executable, [executable] + options)

if __name__ == '__main__':
    main()
