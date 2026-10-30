#!/usr/bin/env python3
"""The Deep Void data 26.1.2 -> 26.2 (idempotent; repo root, after tools/port_resources_26_1.py):
- lakes name what they may replace and where they may go (1.21's checks of LakeFeature, now data)
- trees: dirt_provider / force_dirt -> below_trunk_provider (a rule as vanilla's: not under blocks of
  minecraft:cannot_replace_below_tree_trunk, unless the tree forced its dirt)
"""
import glob
import json

DATA = 'common/src/main/resources/data/'


def not_tag(tag):
    return {'type': 'minecraft:not', 'predicate': {'type': 'minecraft:matching_block_tag', 'tag': tag}}


def walk(node, fix):
    if isinstance(node, dict):
        fix(node)
        for v in node.values():
            walk(v, fix)
    elif isinstance(node, list):
        for v in node:
            walk(v, fix)


def fix(node):
    if node.get('type') == 'minecraft:lake' and isinstance(node.get('config'), dict):
        config = node['config']
        config.setdefault('can_place_feature', {'type': 'minecraft:true'})
        config.setdefault('can_replace_with_air_or_fluid', not_tag('minecraft:features_cannot_replace'))
        config.setdefault('can_replace_with_barrier', not_tag('minecraft:lava_pool_stone_cannot_replace'))
    # vanilla trees and the mod's own features with a TreeConfiguration
    if isinstance(node.get('config'), dict) and 'dirt_provider' in node['config'] and 'trunk_provider' in node['config']:
        config = node['config']
        dirt = config.pop('dirt_provider')
        force = config.pop('force_dirt', False)
        config['below_trunk_provider'] = dirt if force else {
            'type': 'minecraft:rule_based_state_provider', 'rules': [{'if_true': not_tag('minecraft:cannot_replace_below_tree_trunk'), 'then': dirt}]}


def main():
    changed = 0
    for path in glob.glob(DATA + '*/worldgen/configured_feature/*.json'):
        node = json.load(open(path, encoding='utf-8'))
        before = json.dumps(node)
        walk(node, fix)
        if json.dumps(node) != before:
            with open(path, 'w', encoding='utf-8', newline='\n') as f:
                f.write(json.dumps(node, indent=2) + '\n')
            changed += 1
    print('configured features changed', changed)


if __name__ == '__main__':
    main()
