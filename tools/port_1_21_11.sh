#!/bin/sh
# The common code of 1.21.11 from the NeoForge 1.21.1 originals of main, every step idempotent:
# restore -> split (port_multiloader) -> 1.21.11 rules (Lurker's, then the mod's own) -> GeckoLib 5 -> moved imports (against the 1.21.11 sources).
set -e
cd "$(dirname "$0")/.."
tools/restore_common.sh main
python3 -I tools/port_multiloader.py | grep -v '^not indexed' || true
python3 -I tools/port_1_21_11.py
python3 -I tools/port_dv_1_21_11.py
python3 -I tools/port_geckolib5.py
python3 -I tools/port_dv_geckolib5.py
python3 -I tools/port_dv_render_1_21_11.py
src=build/mc-sources-1.21.11
if [ ! -d "$src" ]; then
    jar=$(ls common/build/moddev/artifacts/vanilla-1.21.11-*-sources.jar | head -1)
    mkdir -p "$src" && (cd "$src" && unzip -q -o "$OLDPWD/$jar")
fi
find common/src/main/java -name '*.java' -print0 | xargs -0 python3 -I tools/fix_moved_imports.py "$src" | grep -v '^$' | sort | uniq -c | sort -rn | head -20 || true
python3 -I tools/port_multiloader.py | grep -v "^not indexed" || true
