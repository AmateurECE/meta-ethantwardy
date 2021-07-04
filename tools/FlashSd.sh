#!/bin/bash
###############################################################################
# NAME:             Flash.sh
#
# AUTHOR:           Ethan D. Twardy <edtwardy@mtu.edu>
#
# DESCRIPTION:      The script for flashing the image to the disk
#
# CREATED:          03/20/2021
#
# LAST EDITED:      04/18/2021
###

read -r -d '' USAGE<<EOF
Usage: $0 <imageFile> <outputDisk>
EOF

if [[ $UID != 0 ]]; then
    >&2 printf 'Error: %s\n' "This script must be run as root."
    exit 1
fi

imageFile=$1
outputDisk=$2

if [[ -z $outputDisk || -z $imageFile ]]; then
    >&2 printf '%s\n' "$USAGE"
    exit 1
fi

dd if=$imageFile | pv -s $(($(du $imageFile | awk '{print $1}')*512)) \
                       | dd of=$outputDisk bs=64k

###############################################################################
