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
# LAST EDITED:      09/25/2021
###

podman run -it --rm $CONTAINER_USER \
       -v cortexa72-poky-linux:/home/yocto/poky_sdk \
       -v $PWD:/home/yocto/app \
       -v $HOME/.gitconfig:/home/yocto/.gitconfig \
       localhost/esdk-container

###############################################################################
