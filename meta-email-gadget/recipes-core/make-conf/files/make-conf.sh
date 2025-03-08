#!/bin/sh
#
# SPDX-License-Identifier: MIT
#

### BEGIN INIT INFO
# Provides:             make-conf
# Required-Start:       $local_fs
# Required-Stop:        $local_fs
# Default-Start:        S
# Default-Stop:
# Short-Description:  Generate configuration data for runtime use
### END INIT INFO

# Set up an overlay so that /etc is writeable
mkdir /run/make-conf
mount -t tmpfs tmpfs /run/make-conf
mkdir /run/make-conf/.work /run/make-conf/.upper
mount -t overlay overlay \
  -olowerdir=/etc/postfix,upperdir=/run/make-conf/.upper,workdir=/run/make-conf/.work \
  /etc/postfix/

# Generate the postfix configurationf file
m4 /data/mail/main.cf.vars.m4 /etc/template/postfix/main.cf.template.m4 >/etc/postfix/main.cf
