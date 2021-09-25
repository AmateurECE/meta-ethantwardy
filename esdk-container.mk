#!/usr/bin/make -f
###############################################################################
# NAME:		    esdk-container.mk
#
# AUTHOR:	    Ethan D. Twardy <ethan.twardy@gmail.com>
#
# DESCRIPTION:	    Builds the eSDK container using the Containerfile.
#
# CREATED:	    09/24/2021
#
# LAST EDITED:	    09/24/2021
###

ifdef SQUASH
squash=--squash
endif

define buildahBud
buildah bud --layers $(squash) -f $(1) -t "$(2):latest"
endef

CONTAINER_NAME=esdk-container
$(CONTAINER_NAME)-build.lock: tools/Containerfile tools/cmd-esdk-container.sh
	$(call buildahBud,$<,$(CONTAINER_NAME))
	touch $@

###############################################################################
