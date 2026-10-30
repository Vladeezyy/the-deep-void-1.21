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


DATA = 'common/src/main/resources/data/'
# exact JSON string values renamed in 1.21.2-1.21.11
DATA_RENAMES = {
    '#minecraft:enchantable/sword': '#minecraft:enchantable/sweeping',
    'minecraft:chain': 'minecraft:iron_chain',
}
# The Deep Void and The Pit have a fixed midnight (1.21.1 fixed_time 18000) and no timelines in 1.21.11: the colors
# 1.21.1 darkened by the time of day are baked into the biomes (sky black, fog as the overworld effects' formula)
FOG_AT_MIDNIGHT = (0.06, 0.06, 0.09)


def rgb(value, scale=(1, 1, 1)):
    value &= 0xFFFFFF
    r, g, b = (value >> 16) & 255, (value >> 8) & 255, value & 255
    return '#%02x%02x%02x' % tuple(round(c * k) for c, k in zip((r, g, b), scale))


def music(m):
    out = {'max_delay': m['max_delay'], 'min_delay': m['min_delay'], 'sound': m['sound']}
    if m.get('replace_current_music'):
        out['replace_current_music'] = True
    return out


def biome(data):
    """1.21.11: biome visuals and sounds are environment attributes; carvers are a list"""
    if 'attributes' in data:
        return data
    effects = data.pop('effects')
    attributes = {}
    sounds = {}
    if 'ambient_sound' in effects:
        sounds['loop'] = effects.pop('ambient_sound')
    if 'additions_sound' in effects:
        sounds['additions'] = effects.pop('additions_sound')
    if 'mood_sound' in effects:
        sounds['mood'] = effects.pop('mood_sound')
    if sounds:
        attributes['minecraft:audio/ambient_sounds'] = sounds
    if 'music' in effects:
        attributes['minecraft:audio/background_music'] = {'default': music(effects.pop('music'))}
    if 'particle' in effects:
        particle = effects.pop('particle')
        options = dict(particle['options'])
        options['type'] = options['type'] if ':' in options['type'] else 'minecraft:' + options['type']
        attributes['minecraft:visual/ambient_particles'] = [{'particle': options, 'probability': particle['probability']}]
    attributes['minecraft:visual/sky_color'] = rgb(effects.pop('sky_color'), (0, 0, 0))
    attributes['minecraft:visual/fog_color'] = rgb(effects.pop('fog_color'), FOG_AT_MIDNIGHT)
    if 'water_fog_color' in effects:
        attributes['minecraft:visual/water_fog_color'] = rgb(effects.pop('water_fog_color'))
    new_effects = {}
    for key in ('foliage_color', 'grass_color', 'water_color', 'dry_foliage_color'):
        if key in effects:
            new_effects[key] = rgb(effects.pop(key))
    new_effects.update(effects)
    carvers = data.get('carvers', [])
    if isinstance(carvers, dict):
        carvers = [c for step in carvers.values() for c in (step if isinstance(step, list) else [step])]
    out = {'attributes': attributes, 'carvers': carvers}
    for key in ('downfall', 'has_precipitation', 'temperature', 'temperature_modifier'):
        if key in data:
            out[key] = data[key]
    out['effects'] = new_effects
    for key in ('features', 'spawn_costs', 'spawners'):
        if key in data:
            out[key] = data[key]
    return out


def noise_router(settings):
    """1.21.11: initial_density_without_jaggedness -> preliminary_surface_level (the top surface of the old density,
    shifted by the old threshold, as vanilla's own conversion)"""
    router = settings.get('noise_router', {})
    if 'initial_density_without_jaggedness' not in router:
        return settings
    noise = settings['noise']
    old = router.pop('initial_density_without_jaggedness')
    router['preliminary_surface_level'] = {'type': 'minecraft:find_top_surface', 'density': {'type': 'minecraft:add', 'argument1': -0.390625, 'argument2': old},
                                           'cell_height': noise['size_vertical'] * 4, 'lower_bound': noise['min_y'], 'upper_bound': noise['min_y'] + noise['height']}
    return settings


INGREDIENT_TAGS = DATA + 'the_deep_void/tags/item/recipe_ingredients/'


def ingredient(node, name):
    """1.21.2: an ingredient is an id, "#tag" or a list of ids; a list with tags becomes a tag of its own"""
    if isinstance(node, dict) and set(node) == {'item'}:
        return node['item']
    if isinstance(node, dict) and set(node) == {'tag'}:
        return '#' + node['tag']
    if isinstance(node, list):
        values = [ingredient(v, name) for v in node]
        if len(values) == 1:
            return values[0]
        if any(isinstance(v, str) and v.startswith('#') for v in values):
            write_json(INGREDIENT_TAGS + name + '.json', {'replace': False, 'values': values})
            return '#the_deep_void:recipe_ingredients/' + name
        return values
    return node


def recipe(data, name, patterns):
    for key in ('ingredient', 'base', 'addition', 'template'):
        if key in data:
            data[key] = ingredient(data[key], f'{name}_{key}')
    if 'key' in data:
        data['key'] = {k: ingredient(v, f'{name}_{k}') for k, v in data['key'].items()}
    if 'ingredients' in data:
        data['ingredients'] = [ingredient(v, f'{name}_{i}') for i, v in enumerate(data['ingredients'])]
    # 1.21.5: the trim recipe names its pattern (the pattern no longer names its template)
    if data.get('type') == 'minecraft:smithing_trim' and 'pattern' not in data and data.get('template') in patterns:
        data['pattern'] = patterns[data['template']]
    return data


def trim_patterns():
    """template item -> trim pattern (1.21.1 trim patterns named their template item)"""
    out = {}
    for path in glob.glob(DATA + '*/trim_pattern/*.json'):
        node = read_json(path)
        ns = path.split('/data/')[1].split('/')[0]
        name = os.path.basename(path)[:-5]
        if 'template_item' in node:
            out[node.pop('template_item')] = f'{ns}:{name}'
            node.setdefault('decal', False)
            write_json(path, node)
    return out


TRIM_PROVIDERS = 'common/src/main/resources/the_deep_void_trim_materials.json'


def trim_materials():
    """1.21.5: a trim material comes from its item's provides_trim_material component (port.PortTrimMaterials
    reads the item -> material map written here); the material lost its ingredient and model index"""
    providers = read_json(TRIM_PROVIDERS) if os.path.exists(TRIM_PROVIDERS) else {}
    for path in glob.glob(DATA + '*/trim_material/*.json'):
        node = read_json(path)
        ns = path.split('/data/')[1].split('/')[0]
        if 'ingredient' in node:
            providers[node.pop('ingredient')] = f'{ns}:{os.path.basename(path)[:-5]}'
            node.pop('item_model_index', None)
            write_json(path, node)
    write_json(TRIM_PROVIDERS, dict(sorted(providers.items())))


def loot_without_air(data):
    """1.21.11: a loot item entry may not be air (MCreator's "drops nothing"); empty pools go"""
    pools = []
    for pool in data.get('pools', []):
        entries = [e for e in pool.get('entries', []) if not (e.get('type') == 'minecraft:item' and e.get('name') == 'minecraft:air')]
        if entries:
            pool['entries'] = entries
            pools.append(pool)
    if 'pools' in data:
        data['pools'] = pools
    return data


def rename_values(node):
    if isinstance(node, dict):
        return {k: rename_values(v) for k, v in node.items()}
    if isinstance(node, list):
        return [rename_values(v) for v in node]
    if isinstance(node, str):
        return DATA_RENAMES.get(node, node)
    return node


def convert_data():
    changed = 0
    patterns = trim_patterns()
    trim_materials()
    for path in glob.glob(DATA + '**/*.json', recursive=True):
        try:
            node = read_json(path)
        except ValueError:
            # lenient JSON (Minecraft reads it); left as it is
            print('not strict JSON, skipped:', path)
            continue
        out = rename_values(node)
        if re.search(r'/data/[^/]+/worldgen/biome/', path):
            out = biome(out)
        if re.search(r'/data/[^/]+/recipe/', path):
            out = recipe(out, os.path.basename(path)[:-5], patterns)
        if re.search(r'/data/[^/]+/loot_table/', path):
            out = loot_without_air(out)
        if re.search(r'/data/[^/]+/worldgen/noise_settings/', path):
            out = noise_router(out)
        if re.search(r'/data/[^/]+/dimension/', path) and isinstance(out.get('generator', {}).get('settings'), dict):
            noise_router(out['generator']['settings'])
        if out != node:
            changed += write_json(path, out)
    return changed


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
    print("data files changed", convert_data())
    print(f'models changed {changed}; item definitions written {written}; no model: {missing}; equipment assets {count}, textures moved {moved}')


if __name__ == '__main__':
    main()
