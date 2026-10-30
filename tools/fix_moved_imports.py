#!/usr/bin/env python3
"""Rewrites imports of classes that moved to another package, using the decompiled sources of the target version.
Usage: fix_moved_imports.py <mc-sources dir> <java files...>. An import whose class is missing in the sources is
looked up by its simple name (top-level or nested class); a single match replaces it, several are printed."""
import os, re, sys

src = sys.argv[1]
index = {}
for dirpath, _, files in os.walk(src):
    for f in files:
        if f.endswith('.java'):
            rel = os.path.relpath(os.path.join(dirpath, f), src)[:-5].replace(os.sep, '.')
            index.setdefault(f[:-5], set()).add(rel)
            # nested classes: public static class / record / interface / enum X inside
            try:
                for m in re.finditer(r'^[ \t]+(?:public |protected )?(?:static )?(?:final )?(?:abstract )?(?:class|record|interface|enum) (\w+)', open(os.path.join(dirpath, f), errors='ignore').read(), re.M):
                    index.setdefault(m.group(1), set()).add(rel + '.' + m.group(1))
            except OSError:
                pass

def exists(fqn):
    parts = fqn.split('.')
    for i in range(len(parts), 0, -1):
        path = os.path.join(src, *parts[:i]) + '.java'
        if os.path.exists(path):
            return True
    return False

for path in sys.argv[2:]:
    s = open(path).read()
    o = s
    for m in re.finditer(r'^import (static )?(net\.minecraft\.[\w.]+|com\.mojang\.(?:blaze3d|math)\.[\w.]+);', s, re.M):
        fqn = m.group(2)
        if m.group(1):
            continue
        if exists(fqn):
            continue
        name = fqn.split('.')[-1]
        cands = sorted(c for c in index.get(name, ()) if c.startswith(('net.minecraft', 'com.mojang')))
        # several: the one in a sub-package of the old package (entity.projectile -> entity.projectile.arrow)
        old_pkg = fqn.rsplit('.', 1)[0]
        sub = [c for c in cands if c.rsplit('.', 1)[0].startswith(old_pkg + '.') or c.rsplit('.', 2)[0] == old_pkg]
        if len(cands) > 1 and len(sub) == 1:
            cands = sub
        if len(cands) == 1:
            s = s.replace('import ' + fqn + ';', 'import ' + cands[0] + ';')
            # a nested class imported by its outer name (AbstractArrow.Pickup) keeps the outer path
        else:
            print(path, fqn, '->', cands or 'not found')
    if s != o:
        open(path, 'w').write(s)
