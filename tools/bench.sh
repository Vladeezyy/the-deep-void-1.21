#!/bin/sh
# Tick cost of the mod's tick handlers: the bench scene (DevScene, PortBench) in the dev client, through the hang
# watchdog. Prints the [bench] lines and appends them to build/bench/<label>.txt. Usage: [LOADER=neoforge|fabric] tools/bench.sh [label]
{
cd "$(dirname "$0")/.."
. tools/watchdog.sh
# a thread dump first: a hang shows where it waits
LOADER=${LOADER:-neoforge}
case $LOADER in
	neoforge) PROC='fml.modFolders=the_deep_void' ;;
	fabric) PROC="$(basename "$PWD")/(fabric/)?\.gradle/loom-cache|deepvoid\.scene" ;;
esac
kill_dev() {
    for pid in $(pgrep -f "$PROC"); do jstack "$pid" > build-bench-threads.txt 2>&1; done
    pkill -f "$PROC" 2>/dev/null
}
label=${1:-run}
mkdir -p build/bench
LOG=build-bench.log
# a fresh world every run (same seed): a reopened bench world waited on a screen once
rm -rf $LOADER/run/saves/dv_bench
run_watched "$LOG" 1200 ./gradlew :$LOADER:runScene -Pscene=bench --console=plain
code=$?
grep -E 'Minecraft Crash Report|HUNG' "$LOG" | head -3
grep -oE '\[bench\] (phase|profile|index).*' "$LOG" | tee -a "build/bench/$LOADER-$label.txt"
exit $code
}
