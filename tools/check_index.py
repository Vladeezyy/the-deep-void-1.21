#!/usr/bin/env python3
"""Every @SubscribeEvent method of common has to be on the mod's bus: in port/bus/EventIndex.java or
ClientEventIndex.java, or (the key and GUI messages' registerMessage) called from TheDeepVoidMod.init().
1.99.3.1.3 lost the world load trigger this way (the cult temple, the Weaver's sepulcher and the pit were never placed).

    python3 -I tools/check_index.py      # from the repository root; exit 1 with the missing methods
"""
import re
import sys
from pathlib import Path

ROOT = Path("common/src/main/java/net/sombrius/thedeepvoid")
BUS = ROOT / "port" / "bus"
index = (BUS / "EventIndex.java").read_text() + (BUS / "ClientEventIndex.java").read_text()
init = (ROOT / "TheDeepVoidMod.java").read_text()
SUB = re.compile(r"@SubscribeEvent[^\n]*\n(?:[ \t]*@[^\n]*\n)*\s*public\s+static\s+void\s+(\w+)\(\s*([\w.]+)\s+\w+")
missing = []
for path in sorted(ROOT.rglob("*.java")):
    if BUS in path.parents:
        continue
    for m in SUB.finditer(path.read_text()):
        cls, method = path.stem, m.group(1)
        if re.search(rf"\b{cls}(\.\w+)?::{method}\b", index) or re.search(rf"\b{cls}\.{method}\(", init):
            continue
        missing.append(f"{path.relative_to(ROOT)}: {method}({m.group(2)})")
for line in missing:
    print("not on the mod's bus:", line)
sys.exit(1 if missing else 0)
