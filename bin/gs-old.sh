dir=$(dirname "$0")


export GS=$dir/../target/heapSpank-0.3.jar:$dir/heapSpank-0.3.jar

export PID_TO_MONITOR=$1
export INTERVAL=1s
export OPTION=-gcold

$JAVA_HOME/bin/jstat $OPTION $PID_TO_MONITOR $INTERVAL | java -classpath $GS com.github.eostermueller.heapspank.garbagespank.console.GarbageSpankConsole -i $INTERVAL
