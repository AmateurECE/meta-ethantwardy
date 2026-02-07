#!/bin/sh

start_redis() {
        echo "Starting ${SERVICE}..."
        sysctl -q vm.overcommit_memory=1
        start-stop-daemon --start --pidfile ${PIDFILE} \
          --chuid "redis:rspamd" -- /usr/bin/redis-server $ARGS
}

case "$1" in
    start)
        start_redis
	;;
    stop)
        echo "Stopping ${SERVICE}..."
        start-stop-daemon --stop --pidfile ${PIDFILE}
	;;
    restart)
        echo "Stopping ${SERVICE}..."
        start-stop-daemon --stop --pidfile ${PIDFILE}

        # Since busybox implementation ignores --retry arguments repeatedly check
        # if the process is still running and try another signal after a timeout,
        # efectively simulating a stop with --retry=TERM/5/KILL/5 schedule.
        waitAfterTerm=5000000 # us / 5000 ms / 5 s
        waitAfterKill=5000000 # us / 5000 ms / 5 s
        waitStep=100000 # us / 100 ms / 0.1 s
        waited=0
        start-stop-daemon --stop --test --quiet --pidfile ${PIDFILE}
        processOff=$?
        while [ $processOff -eq 0 ] && [ $waited -le $waitAfterTerm ] ; do
            usleep ${waitStep}
            ((waited+=${waitStep}))
            start-stop-daemon --stop --test --quiet --pidfile ${PIDFILE}
            processOff=$?
        done
        if [ $processOff -eq 0 ] ; then
            start-stop-daemon --stop --signal KILL --pidfile ${PIDFILE}
            rm -f ${PIDFILE}
            start-stop-daemon --stop --test --quiet --pidfile ${PIDFILE}
            processOff=$?
        fi
        waited=0
        while [ $processOff -eq 0 ] && [ $waited -le $waitAfterKill ] ; do
            usleep ${waitStep}
            ((waited+=${waitStep}))
            start-stop-daemon --stop --test --quiet --pidfile ${PIDFILE}
            processOff=$?
        done
        # Here $processOff will indicate if waiting and retrying according to
        # the schedule ended in a successfull stop or not.

        start_redis
	;;
    *)
	echo "Usage: ${SERVICE} {start|stop|restart}"
	exit 1
	;;
esac

exit 0

