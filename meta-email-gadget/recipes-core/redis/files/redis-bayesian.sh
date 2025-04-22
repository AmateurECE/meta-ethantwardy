#!/bin/sh
#
### BEGIN INIT INFO
# Provides:          redis-bayesian
# Required-Start:    $network
# Required-Stop:     $network
# Default-Start:     S 2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Instance of Redis for Rspamd's Bayesian data.
# Description:       Redis is an open source, advanced key-value store.
#                    http://redis.io
### END INIT INFO

export ARGS="/etc/redis/bayesian.conf"
export SERVICE=redis-bayesian
export PIDFILE=/var/run/redis/${SERVICE}.pid

source /usr/libexec/redis/redis-server.sh $@
