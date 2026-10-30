#!/usr/bin/env python3
"""Tick cost fixes for the MCreator procedures that run every tick (idempotent). Usage: tools/port_perf.py

1. world.getEntitiesOfClass(X.class, box, e -> true) with X one of the entities PortEntities keeps per level
   -> PortEntities.find(world, X.class, box): same list, read from a short per-level list instead of the entity sections
   of a box up to 250 blocks wide.
2. The "look along the view ray" loops: getEntitiesOfClass(Entity.class, box) followed by a loop whose whole body is
   `if (entityiterator instanceof X)` -> PortEntities.find(world, X.class, box) (the other entities were skipped anyway).
3. ResourceKey / TagKey / ResourceLocation built from a string literal on every call -> a static final constant.
"""
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
PROC = ROOT / 'src/main/java/net/sombrius/thedeepvoid/procedures'
# the tick subscribers (PortBench.TICK_SUBSCRIBERS) and the procedures they call every tick
FILES = ['IfPlayerFallsInVoid', 'StalkingStalkerLookedAt', 'TheGaolPlaySounds', 'BoneWalkerOnBoots', 'PlayBossMusic', 'PlayerIsGrabbedByApostle',
         'HeartbeatPlays', 'StalkerBreaksBlocks', 'NecrosedProcedure', 'ChainGauntletToolInInventoryTick', 'PlayerLooksAtStalking', 'PlayerInGloomyDeathgrounds',
         'HunterCooldownWorldTick', 'PlayerIsInThePit', 'PlayerLooksAtStalker', 'StalkingStalkerSpawns', 'PlayerLooksAtVoidDweller', 'RottenArmorAbilityProcedure',
         'EyekinCooldownProcedure', 'PlayerInDarknessNearTheStalker', 'PlayerForgetfulFogAppears', 'SpawnUndertakerInDeepVoid', 'PlayerTickUpdateMask',
         'PlayerIsHallucinating', 'CallOfTheVoidDig', 'PlayerIsInDeepVoid', 'AmbientSound', 'PlayerHasModifiedItemInHand', 'CallOutCooldown', 'VoidVisionSpyglassUsed', 'ModifiersRemoveAttributes']
TRACKED = ['StalkerEntity', 'WatchingStalkerEntity', 'OverseerEntity', 'BigOverseerEntity', 'OverseerPillarEntity', 'OverseerBigPillarEntity', 'AmalgamEntity',
           'VoidDwellerEntity', 'FalseHydraEntity', 'UndertakerEntity']
IMPORT_PORT = 'import net.sombrius.thedeepvoid.port.PortEntities;\n'


def close_paren(s, i):
    """Index of the ')' that closes the '(' at s[i]."""
    depth = 0
    while True:
        c = s[i]
        if c == '(':
            depth += 1
        elif c == ')':
            depth -= 1
            if depth == 0:
                return i
        elif c == '"':
            i = s.index('"', i + 1)
        i += 1


def close_brace(s, i):
    depth = 0
    while True:
        c = s[i]
        if c == '{':
            depth += 1
        elif c == '}':
            depth -= 1
            if depth == 0:
                return i
        elif c == '"':
            i = s.index('"', i + 1)
        i += 1


def call_args(s, start):
    """(end, [args]) of the call whose '(' is at start."""
    end = close_paren(s, start)
    args, depth, last = [], 0, start + 1
    for j in range(start + 1, end):
        c = s[j]
        if c in '([{':
            depth += 1
        elif c in ')]}':
            depth -= 1
        elif c == ',' and depth == 0:
            args.append(s[last:j].strip())
            last = j + 1
    args.append(s[last:end].strip())
    return end, args


def to_find(s, cls_new=None):
    """world.getEntitiesOfClass(X.class, box, e -> true) -> PortEntities.find(world, X.class, box) for tracked X."""
    out, pos = [], 0
    pat = re.compile(r'world\.getEntitiesOfClass\(')
    while (m := pat.search(s, pos)):
        start = m.end() - 1
        end, args = call_args(s, start)
        cls = args[0][:-len('.class')] if args[0].endswith('.class') else None
        if cls in TRACKED and len(args) == 3 and args[2] == 'e -> true':
            out.append(s[pos:m.start()] + f'PortEntities.find(world, {cls}.class, {args[1]})')
        else:
            out.append(s[pos:end + 1])
        pos = end + 1
    out.append(s[pos:])
    return ''.join(out)


RAY = re.compile(r'List<Entity> _entfound = world\.getEntitiesOfClass\(Entity\.class, ')


def ray_loops(s):
    """The view ray loops that only act on one tracked class."""
    pos = 0
    while (m := RAY.search(s, pos)):
        start = m.end() - len('Entity.class, ') - 1
        end, args = call_args(s, start)
        rest = s[end + 1:]
        loop = re.match(r'(\.stream\(\)\.sorted\(Comparator\.comparingDouble\(_entcnd -> _entcnd\.distanceToSqr\(_center\)\)\)\.toList\(\);\s*)for \(Entity entityiterator : _entfound\) \{\s*if \(entityiterator instanceof (\w+)\) \{', rest)
        pos = end + 1
        if not loop or loop.group(2) not in TRACKED or args[2] != 'e -> true':
            continue
        cls = loop.group(2)
        for_open = end + 1 + rest.index('{', loop.start(0) + len(loop.group(1)))
        if_open = end + 1 + loop.end(0) - 1
        for_close = close_brace(s, for_open)
        if_close = close_brace(s, if_open)
        if s[if_close + 1:for_close].strip():
            continue  # the loop does more than the instanceof block
        s = s[:m.start()] + f'List<? extends Entity> _entfound = PortEntities.find(world, {cls}.class, {args[1]})' + s[end + 1:]
        pos = m.start() + 1
    return s


CONSTANTS = [
    # (pattern, type, imports needed)
    (re.compile(r'ResourceKey\.create\(Registries\.DIMENSION, ResourceLocation\.parse\("([a-z0-9_.-]+):([a-z0-9_/.-]+)"\)\)'), 'ResourceKey<Level>', 'DIM', ['net.minecraft.world.level.Level']),
    (re.compile(r'TagKey\.create\(Registries\.ENTITY_TYPE, ResourceLocation\.parse\("([a-z0-9_.-]+):([a-z0-9_/.-]+)"\)\)'), 'TagKey<EntityType<?>>', 'ENTITY_TAG', ['net.minecraft.world.entity.EntityType']),
    (re.compile(r'TagKey\.create\(Registries\.BIOME, ResourceLocation\.parse\("([a-z0-9_.-]+):([a-z0-9_/.-]+)"\)\)'), 'TagKey<Biome>', 'BIOME_TAG', ['net.minecraft.world.level.biome.Biome']),
    (re.compile(r'BlockTags\.create\(ResourceLocation\.parse\("([a-z0-9_.-]+):([a-z0-9_/.-]+)"\)\)'), 'TagKey<Block>', 'BLOCK_TAG', ['net.minecraft.world.level.block.Block']),
    (re.compile(r'ItemTags\.create\(ResourceLocation\.parse\("([a-z0-9_.-]+):([a-z0-9_/.-]+)"\)\)'), 'TagKey<Item>', 'ITEM_TAG', ['net.minecraft.world.item.Item']),
    (re.compile(r'ResourceLocation\.fromNamespaceAndPath\("([a-z0-9_.-]+)", "([a-z0-9_/.-]+)"\)'), 'ResourceLocation', 'ID', []),
    (re.compile(r'(?<=\.is\()ResourceLocation\.parse\("([a-z0-9_.-]+):([a-z0-9_/.-]+)"\)(?=\))'), 'ResourceLocation', 'BIOME', []),
]


def constants(s):
    fields = {}
    # constants of an earlier run stay as they are
    done = set(re.findall(r'\n\tprivate static final [^=]+ (\w+) = ', s))
    decl_lines = re.compile(r'^\tprivate static final .*$', re.M)
    kept = decl_lines.findall(s)
    s = decl_lines.sub('\0', s)
    for pat, typ, prefix, imports in CONSTANTS:
        def sub(m):
            name = prefix + '_' + re.sub(r'[^A-Z0-9]', '_', m.group(2).upper())
            if fields.setdefault(name, (typ, m.group(0), imports))[1] != m.group(0):
                raise SystemExit(f'constant clash {name}')
            return name
        s = pat.sub(sub, s)
    for line in kept:
        s = s.replace('\0', line, 1)
    fields = {n: f for n, f in fields.items() if n not in done}
    if not fields:
        return s
    cls = re.search(r'\npublic class \w+ \{\n', s)
    decl = ''.join(f'\tprivate static final {t} {n} = {e};\n' for n, (t, e, _) in sorted(fields.items()))
    s = s[:cls.end()] + decl + '\n' + s[cls.end():]
    for _, (_, _, imports) in fields.items():
        for imp in imports:
            s = add_import(s, imp)
    if 'TagKey<' in decl:
        s = add_import(s, 'net.minecraft.tags.TagKey')
    return s


def add_import(s, name):
    line = f'import {name};\n'
    if line in s:
        return s
    i = s.index('\nimport ') + 1
    return s[:i] + line + s[i:]


def main():
    changed = 0
    for name in FILES:
        p = PROC / f'{name}Procedure.java'
        s = p.read_text()
        new = constants(ray_loops(to_find(s)))
        if 'PortEntities.' in new and IMPORT_PORT not in new:
            new = add_import(new, 'net.sombrius.thedeepvoid.port.PortEntities')
        if new != s:
            p.write_text(new)
            changed += 1
            print('changed', p.name)
    print(changed, 'files changed')


if __name__ == '__main__':
    sys.exit(main())
