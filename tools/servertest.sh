#!/bin/sh
# Starts the dev server (LOADER=neoforge|fabric) on a fresh world through the hang watchdog, kills it once it is up or
# has failed, prints a summary. Log: build-server-<loader>.log
{
cd "$(dirname "$0")/.."
. tools/watchdog.sh
LOADER=${LOADER:-neoforge}
case $LOADER in
	neoforge) PROC='fml.modFolders=the_deep_void' ;;
	fabric) PROC="$(basename "$PWD")/(fabric/)?\.gradle/loom-cache|$(basename "$PWD")/.*net.fabricmc" ;;
	*) echo "LOADER must be neoforge or fabric"; exit 2 ;;
esac
kill_dev() { pkill -f "$PROC" 2>/dev/null; }
LOG=build-server-$LOADER.log
kill_dev
mkdir -p $LOADER/run && echo "eula=true" > $LOADER/run/eula.txt
rm -rf $LOADER/run/world $LOG
./gradlew :$LOADER:runServer --console=plain > $LOG 2>&1 &
wait_started $LOG 'Registry loading errors|FAILED TO BIND|Could not create the Java Virtual Machine'
code=$?
sleep 3
kill_dev; sleep 2; pkill -9 -f "$PROC" 2>/dev/null
wait 2>/dev/null
grep -E 'Done \(|Minecraft Crash Report|Failed to start|Registry loading errors|FAILED TO BIND|HUNG|TIMEOUT' $LOG | head -3
echo "--- errors"
grep -E '/(ERROR|FATAL)\]|\[.*(ERROR|FATAL).*\]|Caused by|Exception:' $LOG | sed -E 's/^\[[^]]*\] //' | cut -c1-300 | sort | uniq -c | sort -rn | head -${1:-40}
exit $code
}
