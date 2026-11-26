#!/bin/sh
# Thaumaturge compat self-test (NeoForge only): starts the NeoForge dev server on a fresh world through the hang
# watchdog; compat/ThaumaturgeCheck checks the compat pack on the 40th tick and stops the server.
#   tools/thaumtest.sh          without Thaumaturge: the pack is not there, no DataMapLoader warning
#   WITH=1 tools/thaumtest.sh   with Thaumaturge (-Pwith_thaumaturge): the pack is on, the mod's aspects are registered,
#                               every item and mob of the mod has aspects (lists the ones without)
# Every item's aspects: neoforge/run/thaumtest-aspects.txt. Log: build-thaumtest[-with].log. Exit 0 only on PASS.
{
cd "$(dirname "$0")/.."
. tools/watchdog.sh
PROC='fml.modFolders=the_deep_void'
kill_dev() { pkill -f "$PROC" 2>/dev/null; }
if [ -n "$WITH" ]; then FLAG=-Pwith_thaumaturge; LOG=build-thaumtest-with.log; else FLAG=; LOG=build-thaumtest.log; fi

kill_dev
mkdir -p neoforge/run && echo "eula=true" > neoforge/run/eula.txt
rm -rf neoforge/run/world neoforge/run/thaumtest-aspects.txt
rm -f "$LOG"
./gradlew :neoforge:runServer --console=plain -Pthaumtest=true $FLAG "$@" > "$LOG" 2>&1 &
pid=$!
wait_started "$LOG" 'Registry loading errors|FAILED TO BIND|Could not create the Java Virtual Machine'
code=$?
# the self-test stops the server on the 40th tick; it still has to save the world
n=0
while [ $code -eq 0 ] && kill -0 $pid 2>/dev/null && [ $n -lt 180 ]; do sleep 1; n=$((n + 1)); done
kill_dev; sleep 2; pkill -9 -f "$PROC" 2>/dev/null
wait $pid 2>/dev/null
if [ -n "$WITH" ]; then echo "=== thaumtest with Thaumaturge ($LOG)"; else echo "=== thaumtest without Thaumaturge ($LOG)"; fi
grep -E 'Done \(|Minecraft Crash Report|Failed to start|Registry loading errors|FAILED TO BIND|HUNG|TIMEOUT' "$LOG" | head -3
grep -E '\[thaumtest\]' "$LOG" | sed -E 's/^.*\[thaumtest\]/[thaumtest]/' | cut -c1-2000
echo "--- DataMapLoader and thaumaturge warnings"
grep -E 'DataMapLoader|WARN.*[Tt]haumaturge|[Tt]haumaturge.*WARN' "$LOG" | sed -E 's/^\[[^]]*\] //' | cut -c1-300 | sort | uniq -c | sort -rn | head -20
echo "--- errors"
grep -E '/(ERROR|FATAL)\]|\[.*(ERROR|FATAL).*\]|Caused by|Exception:' "$LOG" | grep -vE '\[thaumtest\]|No data fixer registered for' | sed -E 's/^\[[^]]*\] //' | cut -c1-300 | sort | uniq -c | sort -rn | head -30
[ $code -eq 0 ] && grep -q '\[thaumtest\] PASS' "$LOG"
rc=$?
echo "thaumtest: $([ $rc -eq 0 ] && echo PASS || echo FAIL)"
exit $rc
}
