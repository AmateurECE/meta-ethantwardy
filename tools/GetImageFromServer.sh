#!/bin/sh

: "${USER:=edtwardy}"
: "${IP_ADDRESS:=192.168.1.60}"
: "${IMAGE_DIR:=/home/edtwardy/Git/Yocto/build/tmp/deploy/images}"
: "${MACHINE:=raspberrypi4-64}"
: "${IMAGE_NAME:=core-image-base-raspberrypi4-64.wic.bz2}"
: "${SSH_PORT:=5000}"

scp -P $SSH_PORT $USER@$IP_ADDRESS:$IMAGE_DIR/$MACHINE/$IMAGE_NAME ./

: "${BUNZIP:=1}"

if [ "$BUNZIP" = "1" ]; then
    bunzip2 -f $IMAGE_NAME
fi
