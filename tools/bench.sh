#!/bin/sh
# Tick cost of the mod's tick handlers: the bench scene (DevScene, PortBench) in the dev client, through the hang
# watchdog. Prints the [bench] lines and appends them to build/bench/<label>.txt. Usage: tools/bench.sh [label]
{
cd "$(dirname "$0")/.."
. tools/watchdog.sh
kill_dev() { pkill -f 'fml.modFolders=the_deep_void' 2>/dev/null; }
label=${1:-run}
mkdir -p build/bench
LOG=build-bench.log
run_watched "$LOG" 1200 ./gradlew runScene -Pscene=bench --console=plain
code=$?
grep -E 'Minecraft Crash Report|HUNG' "$LOG" | head -3
grep -oE '\[bench\] phase=.*' "$LOG" | tee -a "build/bench/$label.txt"
exit $code
}
