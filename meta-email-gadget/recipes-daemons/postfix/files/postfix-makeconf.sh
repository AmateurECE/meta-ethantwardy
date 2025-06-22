#!/bin/sh
#
# SPDX-License-Identifier: MIT
#

### BEGIN INIT INFO
# Provides:             postfix-cfg
# Required-Start:       $local_fs
# Required-Stop:        $local_fs
# Default-Start:        S
# Default-Stop:
# Short-Description:  Generate the postfix configuration
### END INIT INFO

# Generate the postfix configuration file
cat /etc/postfix/main.cf.base /etc/postfix/main.cf.local >/etc/postfix/main.cf
