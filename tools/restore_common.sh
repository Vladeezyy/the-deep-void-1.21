#!/bin/sh
# Puts the MCreator sources of common back to the last NeoForge-only commit (before tools/port_multiloader.py), so the
# split script runs on the originals again. Files only in common (port/bus, port/neo, ...) and the hand-ported files
# of tools/manual-files.txt stay.
cd "$(dirname "$0")/.."
base=${1:-main}
git ls-tree -r --name-only "$base" -- src/main/java | while read -r f; do
    rel=${f#src/main/java/}
    grep -qxF "$rel" tools/manual-files.txt && continue
    if grep -qxF "neoforge: $rel" tools/manual-files.txt; then
        mkdir -p "neoforge/src/main/java/$(dirname "$rel")"
        git show "$base:$f" > "neoforge/src/main/java/$rel"
        rm -f "common/src/main/java/$rel"
        continue
    fi
    git show "$base:$f" > "common/$f"
done
