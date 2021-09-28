#!/bin/sh
###############################################################################
# NAME:             ContainerSdk.sh
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Start up a container with an eSDK installed
#
# CREATED:          09/24/2021
#
# LAST EDITED:      09/27/2021
###

: "${SDK_VOLUME:=cortexa72-poky-linux}"

podman run -it --rm $CONTAINER_USER \
       -v $SDK_VOLUME:/home/yocto/poky_sdk \
       -v /home/$USER/Git/Yocto/meta-edtwardy:/home/yocto/meta-edtwardy \
       -v /home/$USER/Git/Yocto/app-workspace:/home/yocto/app \
       -v $HOME/.gitconfig:/home/yocto/.gitconfig \
       localhost/esdk-container $@

###############################################################################
