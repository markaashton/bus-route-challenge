#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
JARFILE="$DIR/target/bus-route-challenge-1.0-SNAPSHOT.jar"
NAME=mashtons-bus-route-service
LOGFILE=$DIR/$NAME.log
SPRING_OPTS="--logging.file=$LOGFILE"
RUN="java -jar $JARFILE"

#todo: consider configure max heap. e.g. comment out RUN above and uncomment the following two lines:
#JVM_OPTS="-Xmx2g"
#RUN="java $JVM_OPTS -jar $JARFILE"

DATA_FILE="$DIR/tests/bus-route-data-file.txt"

PIDFILENAME="application.pid"
PIDFILE=$DIR/$PIDFILENAME

start() {
    if is_running; then
        echo 'Service already running' >&2
        return 1
    else
        if [ -f $PIDFILE ]; then
            rm -f $PIDFILE
        fi
    fi
    local CMD="$RUN $DATA_FILE $SPRING_OPTS & echo \$!"
    sh -c "$CMD" >/dev/null
}

stop() {
    if ! is_running; then
        echo 'Service not running' >&2
        return 1
    fi
    if [ -f $PIDFILE ]; then
        kill -15 $(cat $PIDFILE)
        rm -f $PIDFILE
    else
        #we can't always rely on the pid file...
        kill -15 $(pgrep -f $JARFILE)
    fi
    
    #todo: improve stop with retry mechanism?
}

status() {
    if is_running; then
        echo 'Service is running' >&2
    else
        echo 'Service is not running' >&2
    fi 
}

is_running() {
    if [ -f $PIDFILE ]; then
        if kill -0 $(cat $PIDFILE); then
            return 0
        else
            return 1
        fi
    else
        #todo: check ubuntu for pgrep...
        if pgrep -f $JARFILE > /dev/null; then
            return 0
        else
            return 1
        fi
    fi 
}

display_help() {
    echo "Usage: $0 {start|stop|status|block} DATA_FILE"
}

case $1 in
    start)
        if [ -z "$2" ]; then
            display_help
        else
            DATA_FILE=$2
            start 
        fi
        ;;
    stop)
        stop
        ;;
    status)
        status
        ;;
    block)
        start
        sleep infinity
        ;;
    *)
        display_help
        ;;
esac
