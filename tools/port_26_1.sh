#!/bin/sh
# The 26.1.2 port from the MCreator originals: the 1.21.11 pipeline, then the 26.1 rules (all idempotent; repo root).
# Resources and data: tools/port_resources_1_21_11.py, then tools/port_resources_26_1.py.
set -e
cd "$(dirname "$0")/.."
sh tools/port_1_21_11.sh
python3 -I tools/port_26_1.py
python3 -I tools/port_dv_26_1.py
src=build/mc-sources-26.1.2
if [ ! -d "$src" ]; then
    jar=$(ls common/build/moddev/artifacts/vanilla-26.1.2-*-sources.jar | head -1)
    mkdir -p "$src" && (cd "$src" && unzip -q -o "$OLDPWD/$jar")
fi
find common/src/main/java neoforge/src/main/java fabric/src/main/java -name '*.java' -print0 | xargs -0 python3 -I tools/fix_moved_imports.py "$src" | grep -v '^$' | sort | uniq -c | sort -rn | head -20 || true
python3 -I tools/port_multiloader.py | grep -v "^not indexed" || true
