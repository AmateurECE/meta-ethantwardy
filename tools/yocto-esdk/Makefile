###############################################################################
# NAME:		    Makefile
#
# AUTHOR:	    Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:	    Build script for the yocto-esdk container.
#
# CREATED:	    06/16/2022
#
# LAST EDITED:	    06/16/2022
###

B:=$(shell pwd)/build
$(shell mkdir -p $(B))

IMAGE_NAME=docker.io/edtwardy/yocto-esdk

all: yocto-esdk-build.lock

BUILD_ARTIFACTS += $(B)/Containerfile
BUILD_ARTIFACTS += $(B)/cmd-esdk-container.sh

$(B)/Containerfile: Containerfile; cp $< $@
$(B)/cmd-esdk-container.sh: cmd-esdk-container.sh; cp $< $@

yocto-esdk-build.lock: $(BUILD_ARTIFACTS)
	cd $(B) && buildah bud --layers -t "$(IMAGE_NAME):latest"
	touch $@

###############################################################################
