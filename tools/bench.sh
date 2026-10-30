#!/bin/sh
# Tick cost of the mod's tick handlers: the bench scene (DevScene, PortBench) in the dev client, through the hang
# watchdog. Prints the [bench] lines and appends them to build/bench/<label>.txt. Usage: tools/bench.sh [label]
{
cd "$(dirname "$0")/.."
. tools/watchdog.sh
# a thread dump first: a hang shows where it waits
kill_dev() {
    for pid in $(pgrep -f 'fml.modFolders=the_deep_void'); do jstack "$pid" > build-bench-threads.txt 2>&1; done
    pkill -f 'fml.modFolders=the_deep_void' 2>/dev/null
}
label=${1:-run}
mkdir -p build/bench
LOG=build-bench.log
# a fresh world every run (same seed): a reopened bench world waited on a screen once
rm -rf run/saves/dv_bench
run_watched "$LOG" 1200 ./gradlew runScene -Pscene=bench --console=plain
code=$?
grep -E 'Minecraft Crash Report|HUNG' "$LOG" | head -3
grep -oE '\[bench\] (phase|profile|index).*' "$LOG" | tee -a "build/bench/$label.txt"
exit $code
}
