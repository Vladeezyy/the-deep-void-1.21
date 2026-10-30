#!/usr/bin/env python3
"""The Deep Void resources 1.21.1 -> 1.21.11 (idempotent; repo root, after tools/port_1_21_11.sh):
- item model definitions (assets/the_deep_void/items): every item gets one; MCreator's overrides become
  range_dispatch on the_deep_void:procedure (port.PortItemProperties), "blocking" becomes minecraft:using_item;
  GeckoLib items are geckolib:geckolib special models
- item models: no builtin/entity parent; spawn eggs (1.21.5: no template) are two tinted layers of own textures
- equipment assets (1.21.4) from tools/armor_assets.json, armor textures moved to textures/entity/equipment
"""
import glob
import json
import os
import re
import shutil
import subprocess

JAVA = 'common/src/main/java/net/sombrius/thedeepvoid/'
ASSETS = 'common/src/main/resources/assets/the_deep_void/'
NS = 'the_deep_void'


def read_json(path):
    with open(path, encoding='utf-8') as f:
        return json.load(f)


def write_json(path, data):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    text = json.dumps(data, indent=2) + '\n'
    if os.path.exists(path) and open(path, encoding='utf-8').read() == text:
        return False
    with open(path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(text)
    return True


def block_ids():
    s = open(JAVA + 'init/TheDeepVoidModBlocks.java', encoding='utf-8').read()
    return dict(re.findall(r'RegistryEntry<Block, Block> (\w+) = REGISTRY\.register\("(\w+)"', s))


def items():
    """item id -> item class name (None for block items)"""
    s = open(JAVA + 'init/TheDeepVoidModItems.java', encoding='utf-8').read()
    out = {}
    for m in re.finditer(r'REGISTRY\.register\("(\w+)",\s*(?:\(\) -> new |)(\w+)', s):
        out[m.group(1)] = m.group(2)
    blocks = block_ids()
    for m in re.finditer(r'(?:block|doubleBlock)\(TheDeepVoidModBlocks\.(\w+)\)', s):
        out[blocks[m.group(1)]] = None
    return out


def spawn_egg_colors():
    """MCreator's spawn egg colors (1.21.1 originals on main; 1.21.5 dropped them from the code)"""
    s = subprocess.run(['git', 'show', 'main:src/main/java/net/sombrius/thedeepvoid/init/TheDeepVoidModItems.java'], capture_output=True, text=True, check=True).stdout
    return {m.group(1): (int(m.group(2)), int(m.group(3))) for m in re.finditer(r'REGISTRY\.register\("(\w+)", \(\) -> new DeferredSpawnEggItem\([\w.]+, (-?\d+), (-?\d+)', s)}


def item_class_source(cls):
    paths = glob.glob(JAVA + f'item/{cls}.java')
    return open(paths[0], encoding='utf-8').read() if paths else ''


def model_ref(item_id):
    if os.path.exists(ASSETS + f'models/item/{item_id}.json'):
        return f'{NS}:item/{item_id}'
    if os.path.exists(ASSETS + f'models/block/{item_id}.json'):
        return f'{NS}:block/{item_id}'
    return None


def plain(model):
    return {'type': 'minecraft:model', 'model': model}


def definition(item_id, cls, eggs):
    model = model_ref(item_id)
    if model is None:
        return None
    if cls and 'getGeoItemRenderer' in item_class_source(cls):
        return {'model': {'type': 'minecraft:special', 'base': model, 'model': {'type': 'geckolib:geckolib'}}}
    if item_id in eggs:
        primary, secondary = eggs[item_id]
        return {'model': {'type': 'minecraft:model', 'model': model,
                          'tints': [{'type': 'minecraft:constant', 'value': primary}, {'type': 'minecraft:constant', 'value': secondary}]}}
    data = read_json(ASSETS + f'models/item/{item_id}.json') if model.startswith(f'{NS}:item/') else {}
    overrides = data.get('overrides', [])
    if not overrides:
        return {'model': plain(model)}
    keys = {next(iter(o['predicate'])) for o in overrides}
    if len(keys) > 1:
        # 1.21.1 took the last override whose predicate held: one range_dispatch per override, the earlier ones as fallback
        result = plain(model)
        for o in overrides:
            key, threshold = next(iter(o['predicate'].items()))
            result = {'type': 'minecraft:range_dispatch', 'property': f'{NS}:procedure', 'name': key, 'entries': [{'threshold': threshold, 'model': plain(o['model'])}], 'fallback': result}
        return {'model': result}
    predicate = keys.pop()
    if predicate == 'blocking':
        return {'model': {'type': 'minecraft:condition', 'property': 'minecraft:using_item', 'on_true': plain(overrides[-1]['model']), 'on_false': plain(model)}}
    entries = sorted(({'threshold': o['predicate'][predicate], 'model': plain(o['model'])} for o in overrides), key=lambda e: e['threshold'])
    return {'model': {'type': 'minecraft:range_dispatch', 'property': f'{NS}:procedure', 'name': predicate, 'entries': entries, 'fallback': plain(model)}}


EGG_SIZE = 16


def egg_textures():
    """Own spawn egg template (two grayscale layers tinted by the item definition), drawn here once (Pillow)"""
    if os.path.exists(ASSETS + 'textures/item/spawn_egg_overlay.png'):
        return
    from PIL import Image
    base, spots = Image.new('RGBA', (EGG_SIZE, EGG_SIZE)), Image.new('RGBA', (EGG_SIZE, EGG_SIZE))
    cx, cy = 7.5, 8.5
    for y in range(EGG_SIZE):
        for x in range(EGG_SIZE):
            # an egg: wider at the bottom
            ry = 6.6 if y >= cy else 7.6
            rx = 5.4 if y >= cy else 4.6 + (y - 1) * 0.12
            d = ((x - cx) / rx) ** 2 + ((y - cy) / ry) ** 2
            if d <= 1:
                shade = 255 if d < 0.55 else 225 if d < 0.85 else 185
                if x - cx > 2 and y - cy > 1:
                    shade -= 25
                base.putpixel((x, y), (shade, shade, shade, 255))
    for x, y in ((5, 5), (6, 5), (9, 7), (10, 7), (10, 8), (5, 10), (6, 10), (6, 11), (9, 12), (10, 12), (7, 3), (4, 8)):
        if base.getpixel((x, y))[3]:
            spots.putpixel((x, y), (255, 255, 255, 255))
    os.makedirs(ASSETS + 'textures/item', exist_ok=True)
    base.save(ASSETS + 'textures/item/spawn_egg.png')
    spots.save(ASSETS + 'textures/item/spawn_egg_overlay.png')


def models():
    changed = 0
    for path in glob.glob(ASSETS + 'models/**/*.json', recursive=True):
        data = read_json(path)
        if data.get('parent') in ('builtin/entity', 'minecraft:builtin/entity'):
            del data['parent']
            changed += write_json(path, data)
        elif data.get('parent') in ('item/template_spawn_egg', 'minecraft:item/template_spawn_egg'):
            changed += write_json(path, {'parent': 'minecraft:item/generated', 'textures': {'layer0': f'{NS}:item/spawn_egg', 'layer1': f'{NS}:item/spawn_egg_overlay'}})
    return changed


def equipment():
    """1.21.4: armor layers are equipment assets; the textures move to textures/entity/equipment/humanoid[_leggings]"""
    assets = read_json('tools/armor_assets.json')
    moved, done = 0, set()
    for name, layer in assets.items():
        layer_path = layer.split(':', 1)[1]
        for n, kind in ((1, 'humanoid'), (2, 'humanoid_leggings')):
            old = ASSETS + f'textures/models/armor/{layer_path}_layer_{n}.png'
            new = ASSETS + f'textures/entity/equipment/{kind}/{name}.png'
            if os.path.exists(old) and not os.path.exists(new):
                os.makedirs(os.path.dirname(new), exist_ok=True)
                shutil.copyfile(old, new)
                done.add(old)
                moved += 1
        layers = {}
        for kind in ('humanoid', 'humanoid_leggings'):
            if os.path.exists(ASSETS + f'textures/entity/equipment/{kind}/{name}.png'):
                layers[kind] = [{'texture': f'{NS}:{name}'}]
        write_json(ASSETS + f'equipment/{name}.json', {'layers': layers})
    # several materials may share a layer: the old textures go once all are copied
    for old in done:
        os.remove(old)
    return len(assets), moved


def main():
    eggs = spawn_egg_colors()
    egg_textures()
    changed = models()
    written, missing = 0, []
    for item_id, cls in sorted(items().items()):
        data = definition(item_id, cls, eggs)
        if data is None:
            missing.append(item_id)
            continue
        written += write_json(ASSETS + f'items/{item_id}.json', data)
    count, moved = equipment()
    print(f'models changed {changed}; item definitions written {written}; no model: {missing}; equipment assets {count}, textures moved {moved}')


if __name__ == '__main__':
    main()
