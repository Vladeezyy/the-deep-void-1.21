#!/bin/sh
# Starts the dev server (LOADER=neoforge|fabric) on a fresh world through the hang watchdog. On its first tick the
# server runs the structure self-test (port/PortServerTest: the cult temple, the Weaver's sepulcher and the pit that the
# mod places once per world are there) and stops itself; RESTART=1 then starts it again on the same world (they are not
# placed a second time). Prints a summary; exit 0 only if the server started and the self-test passed.
# Logs: build-server-<loader>.log, build-server-<loader>-restart.log
{
cd "$(dirname "$0")/.."
. tools/watchdog.sh
LOADER=${LOADER:-neoforge}
case $LOADER in
	neoforge) PROC='fml.modFolders=the_deep_void' ;;
	fabric) PROC="fabric\.dli\.config=.*$(basename "$PWD" | sed 's/ /@@0020/g; s/[.+]/\\&/g')/" ;;
	*) echo "LOADER must be neoforge or fabric"; exit 2 ;;
esac
kill_dev() { pkill -f "$PROC" 2>/dev/null; }
ERRORS=${1:-40}

# start MODE LOG: one dev server run with -Pservertest=MODE; returns 0 if it reached "Done" and the self-test passed
start() {
	rm -f "$2"
	./gradlew :$LOADER:runServer --console=plain -Pservertest=$1 > "$2" 2>&1 &
	pid=$!
	wait_started "$2" 'Registry loading errors|FAILED TO BIND|Could not create the Java Virtual Machine'
	code=$?
	# the self-test stops the server after the first tick; it still has to save the world
	n=0
	while [ $code -eq 0 ] && kill -0 $pid 2>/dev/null && [ $n -lt 180 ]; do sleep 1; n=$((n + 1)); done
	kill_dev; sleep 2; pkill -9 -f "$PROC" 2>/dev/null
	wait $pid 2>/dev/null
	echo "=== $LOADER, $1 world ($2)"
	grep -E 'Done \(|Minecraft Crash Report|Failed to start|Registry loading errors|FAILED TO BIND|HUNG|TIMEOUT' "$2" | head -3
	grep -E '\[servertest\]' "$2" | sed -E 's/^.*\[servertest\]/[servertest]/' | cut -c1-300
	echo "--- errors"
	grep -E '/(ERROR|FATAL)\]|\[.*(ERROR|FATAL).*\]|Caused by|Exception:' "$2" | grep -vE '\[servertest\]|No data fixer registered for' | sed -E 's/^\[[^]]*\] //' | cut -c1-300 | sort | uniq -c | sort -rn | head -$ERRORS
	[ $code -eq 0 ] && grep -q '\[servertest\] PASS' "$2"
}

kill_dev
mkdir -p $LOADER/run && echo "eula=true" > $LOADER/run/eula.txt
rm -rf $LOADER/run/world
start fresh build-server-$LOADER.log
rc=$?
if [ $rc -eq 0 ] && [ -n "$RESTART" ]; then
	start restart build-server-$LOADER-restart.log
	rc=$?
fi
echo "servertest $LOADER: $([ $rc -eq 0 ] && echo PASS || echo FAIL)"
exit $rc
}
