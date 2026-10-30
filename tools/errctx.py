"""Prints the source lines of compile errors whose message or symbol matches a pattern: errctx.py <regex> [limit]"""
import re, sys
pat = re.compile(sys.argv[1]); limit = int(sys.argv[2]) if len(sys.argv) > 2 else 15
lines = open('build-compile.log', encoding='utf-8', errors='replace').read().split('\n')
seen = set(); n = 0
for i, l in enumerate(lines):
    m = re.match(r'(/.+?\.java):(\d+): error: (.*)', l)
    if not m:
        continue
    j = i + 1
    while j < len(lines) and j < i + 6 and ': error:' not in lines[j]:
        j += 1
    block = '\n'.join(lines[i:j])
    if not pat.search(block) or (m.group(1), m.group(2)) in seen:
        continue
    seen.add((m.group(1), m.group(2)))
    n += 1
    if n > limit:
        continue
    src = open(m.group(1), encoding='utf-8').read().split('\n')[int(m.group(2)) - 1].strip()
    print(f"{m.group(1).split('/thedeepvoid/')[-1]}:{m.group(2)}: {m.group(3)[:90]}\n    {src[:200]}")
print(f'-- {n} matches')
