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

# Generate the postfix configuration file
m4 /etc/gadget/main.cf.vars.m4 /etc/gadget/main.cf.template.m4 >/etc/postfix/main.cf
