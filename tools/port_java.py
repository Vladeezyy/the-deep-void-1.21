"""Mechanical Forge 1.20.1 / NeoForge 1.20.6 -> NeoForge 1.21.1 rewrites over src/main/java (idempotent).

The rules mirror what MCreator itself changed between the Forge 1.20.1 and NeoForge 1.20.6 builds of The Deep Void
(1697 file pairs at v1.98.1), plus the 1.20.6 -> 1.21.1 API changes. Anything not covered is fixed by hand.
"""
import os, re, sys

ROOT = sys.argv[1] if len(sys.argv) > 1 else 'src/main/java'


# ---------------------------------------------------------------- helpers
def match_paren(s, i):
    """s[i] is an opening bracket; return index of the matching closer."""
    depth = 0; q = None; n = len(s)
    while i < n:
        c = s[i]
        if q:
            if c == '\\': i += 2; continue
            if c == q: q = None
        elif c in '"\'': q = c
        elif c in '([{': depth += 1
        elif c in ')]}':
            depth -= 1
            if depth == 0: return i
        i += 1
    raise ValueError('unbalanced at %d' % i)


def split_args(s, start):
    """s[start] == '('; return (end, [args])."""
    end = match_paren(s, start)
    inner = s[start + 1:end]; args = []; depth = 0; cur = 0; q = None; i = 0
    while i < len(inner):
        c = inner[i]
        if q:
            if c == '\\': i += 2; continue
            if c == q: q = None
        elif c in '"\'': q = c
        elif c in '([{': depth += 1
        elif c in ')]}': depth -= 1
        elif c == ',' and depth == 0:
            args.append(inner[cur:i]); cur = i + 1
        i += 1
    args.append(inner[cur:])
    return end, args


def receiver_start(s, j):
    """s[j] is the '.' before a method call; walk back over the receiver expression."""
    i = j
    while i > 0:
        c = s[i - 1]
        if c == ')' or c == ']':
            # find matching opener backwards
            depth = 0; k = i - 1; q = None
            while k >= 0:
                ch = s[k]
                if ch in ')]': depth += 1
                elif ch in '([':
                    depth -= 1
                    if depth == 0: break
                k -= 1
            i = k
        elif c.isalnum() or c in '_.$':
            i -= 1
        else:
            break
    return i


def stmt_prefix_ok(s, i):
    """True if s[:i] ends at a statement boundary (only whitespace since ; { } or ) / else)."""
    k = i - 1
    while k >= 0 and s[k] in ' \t\r\n': k -= 1
    if k < 0: return True
    if s[k] in ';{}': return True
    if s[k] == ')': return True  # if (...) stmt / for (...) stmt
    if s[max(0, k - 3):k + 1] == 'else': return True
    if s[k] == '>' and s[k-1] == '-': return True  # lambda body
    return False


IMPORTS = {
    'DeferredHolder': 'net.neoforged.neoforge.registries.DeferredHolder',
    'DeferredRegister': 'net.neoforged.neoforge.registries.DeferredRegister',
    'BuiltInRegistries': 'net.minecraft.core.registries.BuiltInRegistries',
    'Registries': 'net.minecraft.core.registries.Registries',
    'DataComponents': 'net.minecraft.core.component.DataComponents',
    'CustomData': 'net.minecraft.world.item.component.CustomData',
    'EventBusSubscriber': 'net.neoforged.fml.common.EventBusSubscriber',
    'NeoForge': 'net.neoforged.neoforge.common.NeoForge',
    'ModConfigSpec': 'net.neoforged.neoforge.common.ModConfigSpec',
    'DeferredSpawnEggItem': 'net.neoforged.neoforge.common.DeferredSpawnEggItem',
    'ServerLevel': 'net.minecraft.server.level.ServerLevel',
    'SynchedEntityData': 'net.minecraft.network.syncher.SynchedEntityData',
    'ResourceLocation': 'net.minecraft.resources.ResourceLocation',
    'Attributes': 'net.minecraft.world.entity.ai.attributes.Attributes',
    'EntityTypeTags': 'net.minecraft.tags.EntityTypeTags',
    'LivingEntity': 'net.minecraft.world.entity.LivingEntity',
    'Explosion': 'net.minecraft.world.level.Explosion',
    'Level': 'net.minecraft.world.level.Level',
    'Item': 'net.minecraft.world.item.Item',
}


def add_imports(s):
    have = set(re.findall(r'^import\s+([\w.]+);', s, re.M))
    simple = {h.rsplit('.', 1)[1] for h in have}
    pkg = re.search(r'^package\s+([\w.]+);', s, re.M)
    body = re.sub(r'^import .*$', '', s, flags=re.M)
    body = re.sub(r'"(?:\\.|[^"\\])*"', '""', body)
    add = []
    for name, fq in IMPORTS.items():
        if name in simple or fq in have: continue
        if pkg and fq.rsplit('.', 1)[0] == pkg.group(1): continue
        if re.search(r'(?<![\w.])' + name + r'\b', body):
            add.append('import %s;' % fq)
    if not add: return s
    m = pkg
    if not m: return s
    pos = m.end()
    return s[:pos] + '\n\n' + '\n'.join(add) + s[pos:]


# ---------------------------------------------------------------- rules
PKG = [
    (r'net\.minecraftforge\.registries\.RegistryObject', 'net.neoforged.neoforge.registries.DeferredHolder'),
    (r'net\.minecraftforge\.registries\.DeferredRegister', 'net.neoforged.neoforge.registries.DeferredRegister'),
    (r'import net\.minecraftforge\.registries\.ForgeRegistries;', 'import net.minecraft.core.registries.BuiltInRegistries;'),
    (r'net\.minecraftforge\.eventbus\.api\.', 'net.neoforged.bus.api.'),
    (r'import net\.minecraftforge\.fml\.common\.Mod;', 'import net.neoforged.fml.common.Mod;\nimport net.neoforged.fml.common.EventBusSubscriber;'),
    (r'net\.minecraftforge\.api\.distmarker\.', 'net.neoforged.api.distmarker.'),
    (r'net\.minecraftforge\.fml\.', 'net.neoforged.fml.'),
    (r'net\.minecraftforge\.common\.ForgeConfigSpec', 'net.neoforged.neoforge.common.ModConfigSpec'),
    (r'net\.minecraftforge\.common\.MinecraftForge', 'net.neoforged.neoforge.common.NeoForge'),
    (r'net\.minecraftforge\.common\.ForgeSpawnEggItem', 'net.neoforged.neoforge.common.DeferredSpawnEggItem'),
    (r'net\.minecraftforge\.event\.', 'net.neoforged.neoforge.event.'),
    (r'net\.minecraftforge\.client\.', 'net.neoforged.neoforge.client.'),
    (r'net\.minecraftforge\.common\.', 'net.neoforged.neoforge.common.'),
    (r'net\.minecraftforge\.items\.', 'net.neoforged.neoforge.items.'),
    (r'net\.minecraftforge\.fluids\.', 'net.neoforged.neoforge.fluids.'),
    # GeckoLib 4.0 (1.20.1) -> 4.5+ package layout
    (r'software\.bernie\.geckolib\.core\.animatable\.instance\.', 'software.bernie.geckolib.animatable.instance.'),
    (r'software\.bernie\.geckolib\.core\.animatable\.model\.CoreGeoBone', 'software.bernie.geckolib.cache.object.GeoBone'),
    (r'software\.bernie\.geckolib\.core\.animatable\.GeoAnimatable', 'software.bernie.geckolib.animatable.GeoAnimatable'),
    (r'software\.bernie\.geckolib\.core\.object\.PlayState', 'software.bernie.geckolib.animation.PlayState'),
    (r'software\.bernie\.geckolib\.core\.object\.DataTicket', 'software.bernie.geckolib.constant.dataticket.DataTicket'),
    (r'software\.bernie\.geckolib\.core\.animation\.', 'software.bernie.geckolib.animation.'),
    (r'software\.bernie\.geckolib\.core\.keyframe\.', 'software.bernie.geckolib.animation.keyframe.'),
    (r'software\.bernie\.geckolib\.util\.RenderUtils\b', 'software.bernie.geckolib.util.RenderUtil'),
    (r'\bRenderUtils\.', 'RenderUtil.'),
    (r'\bCoreGeoBone\b', 'GeoBone'),
]

REG = {  # ForgeRegistries field -> (Registries/BuiltInRegistries field, base type)
    'ITEMS': ('ITEM', 'Item'), 'BLOCKS': ('BLOCK', 'Block'), 'ENTITY_TYPES': ('ENTITY_TYPE', 'EntityType<?>'),
    'SOUND_EVENTS': ('SOUND_EVENT', 'SoundEvent'), 'MOB_EFFECTS': ('MOB_EFFECT', 'MobEffect'),
    'PARTICLE_TYPES': ('PARTICLE_TYPE', 'ParticleType<?>'), 'POTIONS': ('POTION', 'Potion'),
    'BLOCK_ENTITY_TYPES': ('BLOCK_ENTITY_TYPE', 'BlockEntityType<?>'), 'MENU_TYPES': ('MENU', 'MenuType<?>'),
    'ATTRIBUTES': ('ATTRIBUTE', 'Attribute'), 'FLUIDS': ('FLUID', 'Fluid'),
    'RECIPE_SERIALIZERS': ('RECIPE_SERIALIZER', 'RecipeSerializer<?>'), 'RECIPE_TYPES': ('RECIPE_TYPE', 'RecipeType<?>'),
    'FEATURES': ('FEATURE', 'Feature<?>'), 'ENCHANTMENTS': ('ENCHANTMENT', 'Enchantment'),
    'PAINTING_VARIANTS': ('PAINTING_VARIANT', 'PaintingVariant'), 'VILLAGER_PROFESSIONS': ('VILLAGER_PROFESSION', 'VillagerProfession'),
    'POI_TYPES': ('POINT_OF_INTEREST_TYPE', 'PoiType'), 'CREATIVE_MODE_TABS': ('CREATIVE_MODE_TAB', 'CreativeModeTab'),
    'BIOMES': ('BIOME', 'Biome'), 'GAME_EVENTS': ('GAME_EVENT', 'GameEvent'),
}

BASE_OF_ELEMENT = {  # RegistryObject<T> where T is not the registry base type
    'EntityType': 'EntityType<?>', 'BlockEntityType': 'BlockEntityType<?>', 'MenuType': 'MenuType<?>',
    'ParticleType': 'ParticleType<?>', 'SimpleParticleType': 'ParticleType<?>', 'RecipeSerializer': 'RecipeSerializer<?>',
    'RecipeType': 'RecipeType<?>', 'Feature': 'Feature<?>',
}


def fix_registry_objects(s):
    m = re.search(r'DeferredRegister<(.+?)>\s+REGISTRY\s*=', s)
    class_base = m.group(1) if m else None

    out = []; i = 0
    while True:
        j = s.find('RegistryObject<', i)
        if j < 0: out.append(s[i:]); break
        if j > 0 and (s[j - 1].isalnum() or s[j - 1] == '_'):
            out.append(s[i:j + 1]); i = j + 1; continue
        k = j + len('RegistryObject')
        depth = 0; e = k
        while True:
            if s[e] == '<': depth += 1
            elif s[e] == '>':
                depth -= 1
                if depth == 0: break
            e += 1
        elem = s[k + 1:e].strip()
        head = re.match(r'(?:\? extends )?([\w.]+)', elem).group(1)
        if head in BASE_OF_ELEMENT: base = BASE_OF_ELEMENT[head]
        elif head in BASE_TYPES: base = head
        elif class_base and '<' not in elem: base = class_base
        else: base = head if '<' not in elem else elem
        out.append(s[i:j]); out.append('DeferredHolder<%s, %s>' % (base, elem)); i = e + 1
    return ''.join(out)


BASE_TYPES = {'Item', 'Block', 'SoundEvent', 'MobEffect', 'Potion', 'Attribute', 'Fluid', 'Enchantment', 'PaintingVariant',
              'VillagerProfession', 'PoiType', 'CreativeModeTab', 'Biome', 'GameEvent', 'FluidType'}


def fix_forge_registries(s):
    # DeferredRegister.create(ForgeRegistries.X, ...) -> DeferredRegister.create(Registries.Y, ...)
    s = re.sub(r'DeferredRegister\.create\(ForgeRegistries\.(\w+)',
               lambda m: 'DeferredRegister.create(Registries.%s' % REG.get(m.group(1), (m.group(1), ''))[0], s)
    s = re.sub(r'ForgeRegistries\.(\w+)\.getValue\(', lambda m: 'BuiltInRegistries.%s.get(' % REG.get(m.group(1), (m.group(1),))[0], s)
    s = re.sub(r'ForgeRegistries\.(\w+)\.containsKey\(', lambda m: 'BuiltInRegistries.%s.containsKey(' % REG.get(m.group(1), (m.group(1),))[0], s)
    s = re.sub(r'ForgeRegistries\.(\w+)\.getValues\(\)', lambda m: 'BuiltInRegistries.%s.stream().toList()' % REG.get(m.group(1), (m.group(1),))[0], s)
    s = re.sub(r'ForgeRegistries\.(\w+)\.getKeys\(\)', lambda m: 'BuiltInRegistries.%s.keySet()' % REG.get(m.group(1), (m.group(1),))[0], s)
    s = re.sub(r'ForgeRegistries\.(\w+)\b', lambda m: 'BuiltInRegistries.%s' % REG.get(m.group(1), (m.group(1),))[0], s)
    return s


def fix_events(s):
    s = re.sub(r'@Mod\.EventBusSubscriber', '@EventBusSubscriber', s)
    s = s.replace('Mod.EventBusSubscriber.Bus', 'EventBusSubscriber.Bus')
    s = s.replace('MinecraftForge.EVENT_BUS', 'NeoForge.EVENT_BUS')
    s = re.sub(r'\bForgeConfigSpec\b', 'ModConfigSpec', s)
    s = re.sub(r'\bForgeSpawnEggItem\b', 'DeferredSpawnEggItem', s)
    return s


def fix_effect_holders(s):
    # 1.20.5+: MobEffectInstance / hasEffect / getEffect / removeEffect take Holder<MobEffect>; DeferredHolder is one
    s = re.sub(r'\b(TheDeepVoidModMobEffects\.\w+)\.get\(\)', r'\1', s)
    s = re.sub(r'\b(TheDeepVoidModPotions\.\w+)\.get\(\)', r'\1', s)
    return s


TAG_TYPES = {'putDouble': 'double', 'putBoolean': 'boolean', 'putString': 'String', 'putInt': 'int', 'putFloat': 'float', 'putLong': 'long'}


def fix_item_tags(s):
    # writes: R.getOrCreateTag().putX(k, v);  ->  { final String _tagName = k; ... CustomData.update(...) }
    key = '.getOrCreateTag().'
    out = []; i = 0
    while True:
        j = s.find(key, i)
        if j < 0: out.append(s[i:]); break
        r0 = receiver_start(s, j)
        recv = s[r0:j]
        mname = re.match(r'(\w+)\(', s[j + len(key):])
        meth = mname.group(1) if mname else None
        p = j + len(key) + (len(meth) if meth else 0)
        done = False
        if meth and (meth in TAG_TYPES or meth == 'remove') and stmt_prefix_ok(s, r0):
            end, args = split_args(s, p)
            if end + 1 < len(s) and s[end + 1] == ';':
                args = [a.strip() for a in args]
                ind = re.search(r'[ \t]*$', s[:r0]).group(0)
                if meth == 'remove':
                    rep = 'CustomData.update(DataComponents.CUSTOM_DATA, %s, tag -> tag.remove(%s));' % (recv, args[0])
                else:
                    rep = ('{\n%s\tfinal String _tagName = %s;\n%s\tfinal %s _tagValue = %s;\n%s\tCustomData.update(DataComponents.CUSTOM_DATA, %s, tag -> tag.%s(_tagName, _tagValue));\n%s}'
                           % (ind, args[0], ind, TAG_TYPES[meth], args[1], ind, recv, meth, ind))
                out.append(s[i:r0]); out.append(rep); i = end + 2; done = True
        if not done:
            out.append(s[i:j]); out.append('.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().'); i = j + len(key)
    return ''.join(out)


def fix_synched_data(s):
    # 1.20.5: defineSynchedData() { super...; this.entityData.define(A, v); } -> builder form
    m = re.search(r'protected void defineSynchedData\(\)\s*\{', s)
    if not m: return s
    end = match_paren(s, m.end() - 1)
    body = s[m.end():end]
    body = body.replace('super.defineSynchedData();', 'super.defineSynchedData(builder);')
    body = re.sub(r'this\.entityData\.define\(', 'builder.define(', body)
    return s[:m.start()] + 'protected void defineSynchedData(SynchedEntityData.Builder builder) {' + body + s[end:]


def fix_rl(s):
    out = []; i = 0; key = 'new ResourceLocation('
    while True:
        j = s.find(key, i)
        if j < 0: out.append(s[i:]); break
        out.append(s[i:j])
        end, args = split_args(s, j + len(key) - 1)
        args = [a.strip() for a in args]
        if len(args) == 2: out.append('ResourceLocation.fromNamespaceAndPath(%s, %s)' % (args[0], args[1]))
        else: out.append('ResourceLocation.parse(%s)' % args[0])
        i = end + 1
    return ''.join(out)


def fix_misc(s):
    s = re.sub(r'(\w+)\.setMaxUpStep\(([^;]+)\);', r'\1.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(\2);', s)
    s = re.sub(r'(?<![\w.])setMaxUpStep\(([^;]+)\);', r'this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(\1);', s)
    s = s.replace('AttributeModifier.Operation.ADDITION', 'AttributeModifier.Operation.ADD_VALUE')
    s = s.replace('AttributeModifier.Operation.MULTIPLY_BASE', 'AttributeModifier.Operation.ADD_MULTIPLIED_BASE')
    s = s.replace('AttributeModifier.Operation.MULTIPLY_TOTAL', 'AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL')
    s = s.replace('BASE_ATTACK_DAMAGE_UUID', 'BASE_ATTACK_DAMAGE_ID').replace('BASE_ATTACK_SPEED_UUID', 'BASE_ATTACK_SPEED_ID')
    return s


def fix_player_variables(s):
    s = re.sub(r'\((\w+)\.getCapability\(TheDeepVoidModVariables\.PLAYER_VARIABLES_CAPABILITY, null\)\.orElse\(new TheDeepVoidModVariables\.PlayerVariables\(\)\)\)',
               r'\1.getData(TheDeepVoidModVariables.PLAYER_VARIABLES)', s)
    key = '.getCapability(TheDeepVoidModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {'
    while True:
        j = s.find(key)
        if j < 0: break
        r0 = receiver_start(s, j); recv = s[r0:j]
        ob = j + len(key) - 1
        cb = match_paren(s, ob)
        assert s[cb + 1:cb + 3] == ');', s[cb:cb + 10]
        body = s[ob + 1:cb].replace('capability.', '_vars.')
        ind = re.search(r'[ \t]*$', s[:r0]).group(0)
        body = re.sub(r'\n' + ind + r'\t', '\n' + ind, body).rstrip()
        rep = 'TheDeepVoidModVariables.PlayerVariables _vars = %s.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);%s' % (recv, body)
        s = s[:r0] + rep + s[cb + 3:]
    return s


RENDER_METHODS = r'(?:renderToBuffer|preRender|actuallyRender|renderRecursively|postRender|render)'


def fix_render_color(s):
    # 1.21: model / GeckoLib render methods take one packed ARGB int instead of four floats
    names = set()
    def decl(m):
        names.add((m.group(2), m.group(3), m.group(4), m.group(5)))
        return m.group(1) + 'int color)'
    s = re.sub(r'(public void ' + RENDER_METHODS + r'\([^{;]*?)float (\w+),\s*float (\w+),\s*float (\w+),\s*float (\w+)\)', decl, s)
    for r, g, b, a in names:
        s = re.sub(r'\b%s,\s*%s,\s*%s,\s*%s\)' % (r, g, b, a), 'color)', s)
    s = re.sub(r'(\.reRender\([^;]*?OverlayTexture\.NO_OVERLAY),\s*1,\s*1,\s*1,\s*1\)', r'\1, -1)', s)
    return s


def remove_method(s, sig_regex):
    """Remove '@Override? <sig> { ... }' blocks whose signature matches sig_regex."""
    while True:
        m = re.search(r'\n([ \t]*)(?:@Override\s*\n[ \t]*)?' + sig_regex + r'[^{;]*\{', s)
        if not m: return s
        end = match_paren(s, m.end() - 1)
        s = s[:m.start()] + s[end + 1:]


def fix_121(s):
    s = fix_render_color(s)
    s = remove_method(s, r'public Packet<ClientGamePacketListener> getAddEntityPacket\(\)')
    s = re.sub(r'public int getUseDuration\(ItemStack (\w+)\)', r'public int getUseDuration(ItemStack \1, LivingEntity entity)', s)
    s = re.sub(r'\.getUseDuration\(\)', '.getUseDuration(null)', s)
    s = re.sub(r'public boolean canChangeDimensions\(\)', 'public boolean canChangeDimensions(Level from, Level to)', s)
    s = re.sub(r'public boolean ignoreExplosion\(\)', 'public boolean ignoreExplosion(Explosion explosion)', s)
    s = re.sub(r'public void appendHoverText\(ItemStack (\w+), Level (\w+), List<Component> (\w+), TooltipFlag (\w+)\)',
               r'public void appendHoverText(ItemStack \1, Item.TooltipContext \2, List<Component> \3, TooltipFlag \4)', s)
    s = re.sub(r'super\.appendHoverText\((\w+), (\w+), (\w+), (\w+)\)', r'super.appendHoverText(\1, \2, \3, \4)', s)
    return s


def convert(s):
    o = s
    for a, b in PKG: s = re.sub(a, b, s)
    s = fix_rl(s)
    s = fix_registry_objects(s)
    s = fix_forge_registries(s)
    s = fix_events(s)
    s = fix_effect_holders(s)
    s = fix_item_tags(s)
    s = fix_player_variables(s)
    s = fix_synched_data(s)
    s = fix_misc(s)
    s = fix_121(s)
    if s != o: s = add_imports(s)
    # drop duplicate imports
    seen = set(); lines = []
    for l in s.split('\n'):
        if l.startswith('import '):
            if l in seen: continue
            seen.add(l)
        lines.append(l)
    return '\n'.join(lines)


if __name__ == '__main__':
    only = set(open(sys.argv[2]).read().split()) if len(sys.argv) > 2 else None
    n = 0
    for dp, _, fs in os.walk(ROOT):
        for f in fs:
            if not f.endswith('.java'): continue
            p = os.path.join(dp, f)
            s = open(p, encoding='utf-8').read()
            try:
                t = convert(s)
            except Exception as e:
                print('FAILED', p, e); continue
            if t != s:
                open(p, 'w', encoding='utf-8').write(t); n += 1
    print('changed', n)
