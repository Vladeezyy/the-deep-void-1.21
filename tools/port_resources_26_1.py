#!/usr/bin/env python3
"""The Deep Void data 1.21.11 -> 26.1.2 (idempotent; repo root, after tools/port_resources_1_21_11.py):
- 26.1 has no minecraft:random_patch: the configured feature becomes the patch's inner feature, and every placed
  feature of it gets the patch as placement modifiers (count of the tries, a random offset of the spreads, the inner
  feature's own placement), as vanilla's patches are written in 26.1
- dimension types name the ender dragon fight
"""
import glob
import json
import os

DATA = 'common/src/main/resources/data/'


def read_json(path):
    with open(path, encoding='utf-8') as f:
        return json.load(f)


def write_json(path, data):
    with open(path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(json.dumps(data, indent=2) + '\n')


def trapezoid(spread):
    return {'type': 'minecraft:trapezoid', 'max': spread, 'min': -spread, 'plateau': 0}


def random_patches():
    patches = {}
    for path in glob.glob(DATA + '*/worldgen/configured_feature/*.json'):
        node = read_json(path)
        if node.get('type') != 'minecraft:random_patch':
            continue
        config = node['config']
        inner = config['feature']
        if not isinstance(inner, dict) or not isinstance(inner.get('feature'), dict):
            raise SystemExit(f'{path}: random_patch with a feature reference, convert by hand')
        ns = path.split('/data/')[1].split('/')[0]
        patches[f'{ns}:{os.path.basename(path)[:-5]}'] = [
            {'type': 'minecraft:count', 'count': config.get('tries', 128)},
            {'type': 'minecraft:random_offset', 'xz_spread': trapezoid(config.get('xz_spread', 7)), 'y_spread': trapezoid(config.get('y_spread', 3))},
        ] + inner.get('placement', [])
        write_json(path, inner['feature'])
    placed = 0
    if patches:
        for path in glob.glob(DATA + '*/worldgen/placed_feature/*.json'):
            node = read_json(path)
            modifiers = patches.get(node.get('feature')) if isinstance(node.get('feature'), str) else None
            if modifiers:
                node['placement'] = node.get('placement', []) + modifiers
                write_json(path, node)
                placed += 1
    return len(patches), placed


def lakes():
    """26.1: LakeFeature moves its origin by (-8, -4, -8) itself; 1.21 lakes were placed with that offset (MCreator's
    random_offset -8), twice the offset reaches chunks outside the generation region (a crash)"""
    lakes = set()
    for path in glob.glob(DATA + '*/worldgen/configured_feature/*.json'):
        if read_json(path).get('type') == 'minecraft:lake':
            lakes.add(path.split('/data/')[1].split('/')[0] + ':' + os.path.basename(path)[:-5])
    changed = 0
    for path in glob.glob(DATA + '*/worldgen/placed_feature/*.json'):
        node = read_json(path)
        if node.get('feature') not in lakes:
            continue
        placement = [m for m in node.get('placement', []) if not (m.get('type') == 'minecraft:random_offset' and m.get('xz_spread') == -8 and m.get('y_spread') == 0)]
        if placement != node.get('placement', []):
            node['placement'] = placement
            write_json(path, node)
            changed += 1
    return changed


def dimension_types():
    changed = 0
    for path in glob.glob(DATA + '*/dimension_type/*.json'):
        node = read_json(path)
        before = json.dumps(node)
        node.setdefault('has_ender_dragon_fight', False)
        # 26.1: the minimum light of a dimension is an attribute (black by default); vanilla's own 1.21 overworld
        # migrated to #0a0a0a, the mod's dimensions (ambient light 0 like the overworld) get the same
        node.setdefault('attributes', {}).setdefault('minecraft:visual/ambient_light_color', '#0a0a0a')
        if json.dumps(node) != before:
            write_json(path, dict(sorted(node.items())))
            changed += 1
    return changed


def main():
    patches, placed = random_patches()
    print(f'random patches {patches} (placed features {placed}); lakes {lakes()}; dimension types {dimension_types()}')


if __name__ == '__main__':
    main()
