#!/bin/sh
# Compiles common and prints the error count, the top messages and missing symbols, and errors per package
cd "$(dirname "$0")/.."
perl -e 'alarm 2400; exec @ARGV' ./gradlew :common:compileJava --console=plain > build-compile.log 2>&1
echo "errors: $(grep -c 'error:' build-compile.log)"
grep -oE "error: .*" build-compile.log | sort | uniq -c | sort -rn | head -${1:-25}
grep -E "^\s+symbol" build-compile.log | sed 's/^ *//' | sort | uniq -c | sort -rn | head -${1:-25}
grep -oE "/thedeepvoid/\w+/" build-compile.log | sort | uniq -c | sort -rn | head
