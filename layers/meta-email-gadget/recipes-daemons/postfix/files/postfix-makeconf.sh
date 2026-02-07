#!/bin/sh
#
# SPDX-License-Identifier: MIT
#

### BEGIN INIT INFO
# Provides:             postfix-makeconf
# Required-Start:       $local_fs
# Required-Stop:        $local_fs
# Default-Start:        S
# Default-Stop:
# Short-Description:  Generate the postfix configuration
### END INIT INFO

# Generate the postfix configuration file
base_conf=/etc/postfix/main.cf.base
local_conf=/etc/postfix/main.cf.local
output=/etc/postfix/main.cf

# Escape regex metacharacters in keys safely
escape_regex() {
  sed -E 's/[][(){}.^$+*?|\\]/\\&/g'
}

# Read keys from local_conf
keys=$(grep -E '^\s*[a-zA-Z0-9_.-]+\s*=' "$local_conf" | sed -E 's/^\s*([a-zA-Z0-9_.-]+)\s*=.*/\1/' | escape_regex)

# Create a sed script to remove blocks from base_conf
sed_script=""
for key in $keys; do
  sed_script+="/^$key[[:space:]]*=.*$/{
N
:loop
/^\s/{
N
b loop
}
d
}
"
done

# Apply the sed script to base_conf
sed "$sed_script" "$base_conf" >"$output"

# Append local_conf
cat $local_conf >>$output
