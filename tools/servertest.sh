#!/bin/sh
# Starts the dev server on a fresh world, waits until it is up or has failed, stops it, prints a summary of errors.
cd "$(dirname "$0")/.."
rm -rf run/world build-server.log
( i=0
  until grep -qE 'Done \(|Crash report saved|Minecraft Crash Report|Failed to start|Registry loading errors|BUILD FAILED' build-server.log 2>/dev/null || [ $i -gt 400 ]; do sleep 2; i=$((i+1)); done
  sleep 3; echo stop; sleep 40 ) | ./gradlew runServer --console=plain > build-server.log 2>&1 &
PID=$!
( sleep 900; kill $PID 2>/dev/null; pkill -f 'net.neoforged.*server' ) &
wait $PID
grep -E 'Done \(|Crash report saved|Failed to start|Registry loading errors' build-server.log | head -3
echo "--- errors"
grep -E '/(ERROR|FATAL)\]|Caused by|Exception:' build-server.log | sed -E 's/^\[[^]]*\] //' | cut -c1-300 | sort | uniq -c | sort -rn | head -${1:-40}
