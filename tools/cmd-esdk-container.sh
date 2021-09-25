#!/bin/bash
###############################################################################
# NAME:             cmd-esdk-container.sh
#
# AUTHOR:           Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:      Command script for the eSDK container
#
# CREATED:          09/24/2021
#
# LAST EDITED:      09/25/2021
###

set -e

if compgen -G "$HOME/poky_sdk/environment-setup-*" > /dev/null; then
    . $HOME/poky_sdk/environment-setup-*
fi

/bin/bash

###############################################################################
