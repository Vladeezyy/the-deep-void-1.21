#!/usr/bin/env python3
"""The Deep Void 1.21.11 -> 26.1.2, the mod's own rules after tools/port_26_1.py (idempotent; repo root)"""
import glob
import json
import re

ROOT = 'common/src/main/java/net/sombrius/thedeepvoid/'
PKG = 'net.sombrius.thedeepvoid'

PATH_TYPES = {'DANGER_FIRE': 'FIRE_IN_NEIGHBOR', 'DAMAGE_FIRE': 'FIRE', 'DANGER_OTHER': 'DAMAGING_IN_NEIGHBOR', 'DAMAGE_OTHER': 'DAMAGING'}


def add_import(s, imp):
    if ('import ' + imp + ';') in s:
        return s
    return re.sub(r'^(package [\w.]+;\n)', r'\1\nimport ' + imp + ';', s, count=1, flags=re.M)


def entities(s):
    # 26.1: messages to a player: system chat or the overlay above the hotbar
    s = re.sub(r'\.displayClientMessage\(([^;]*),\s*true\);', r'.sendOverlayMessage(\1);', s)
    s = re.sub(r'\.displayClientMessage\(([^;]*),\s*false\);', r'.sendSystemMessage(\1);', s)
    # 26.1: no entity representation of a stack (the tooltips' holder of the stack): the client player
    s = re.sub(r'itemstack\.getEntityRepresentation\(\) != null \? itemstack\.getEntityRepresentation\(\) : ', '', s)
    s = s.replace('Entity entity = itemstack.getEntityRepresentation();', f'Entity entity = {PKG}.port.PortUtil.clientPlayer.get();')
    # 26.1: an entity type is in a tag through its holder
    s = re.sub(r'\.getType\(\)\.is\((?=TagKey\.create\(Registries\.ENTITY_TYPE|net\.minecraft\.tags\.TagKey\.create\(net\.minecraft\.core\.registries\.Registries\.ENTITY_TYPE|EntityTypeTags\.|[A-Z_]*TAG)',
               '.getType().builtInRegistryHolder().is(', s)
    # 26.1: Level.random is protected
    s = re.sub(r'\b(_serverworld|_level|_serverLevel|_world|world|level)\.random\b(?!\()', r'\1.getRandom()', s)
    # 26.1: a boss bar has an id
    s = s.replace('new ServerBossEvent(this.getDisplayName(), ', 'new ServerBossEvent(java.util.UUID.randomUUID(), this.getDisplayName(), ')
    for a, b in PATH_TYPES.items():
        s = re.sub(r'\bPathType\.' + a + r'\b', 'PathType.' + b, s)
    s = re.sub(r'new ChunkPos\(((?:context\.origin\(\)|\w+\.blockPosition\(\)|pos))\)', r'ChunkPos.containing(\1)', s)
    return s


def blocks(s):
    # 26.1: no post-processing predicate of block properties (emissive rendering stays)
    s = re.sub(r'\.hasPostProcess\(\(bs, br, bp\) -> true\)', '', s)
    s = s.replace('import net.minecraft.client.renderer.ItemBlockRenderTypes;\n', '')
    return s


def render(s):
    # 26.1: entity cutout has no culling by default
    s = s.replace('RenderTypes.entityCutoutNoCull(', 'RenderTypes.entityCutout(').replace('RenderTypes::entityCutoutNoCull', 'RenderTypes::entityCutout')
    s = s.replace('net.minecraft.client.renderer.state.CameraRenderState', 'net.minecraft.client.renderer.state.level.CameraRenderState')
    return s


def screens(s):
    """MCreator container screens: render() only drew the background, the screen and the tooltip"""
    s = s.replace('super.renderContents(guiGraphics, gx, gy, ticks);', 'super.extractContents(guiGraphics, gx, gy, ticks);')
    s = re.sub(r'\n\t@Override\n\tpublic void render\(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks\) \{\n'
               r'\t\tthis\.renderBackground\(guiGraphics, mouseX, mouseY, partialTicks\);\n\t\tsuper\.render\(guiGraphics, mouseX, mouseY, partialTicks\);\n'
               r'\t\tthis\.renderTooltip\(guiGraphics, mouseX, mouseY\);\n\t\}\n', '', s)
    return s


def jei_recipe(s):
    """26.1: a recipe serializer is a record of its codecs; results are item stack templates (the recipes are read
    before the item components are bound); assemble without registries; recipes name their group"""
    m = re.search(r'public class (\w+) implements Recipe<RecipeInput> \{', s)
    if not m or 'ItemStackTemplate output' in s:
        return s
    cls = m.group(1)
    s = s.replace('private final ItemStack output;', 'private final ItemStackTemplate output;')
    s = s.replace(f'public {cls}(ItemStack output, ', f'public {cls}(ItemStackTemplate output, ')
    s = s.replace('public ItemStack assemble(RecipeInput pContainer, HolderLookup.Provider provider) {\n\t\treturn output;',
                  'public ItemStack assemble(RecipeInput pContainer) {\n\t\treturn output.create();')
    s = s.replace('public ItemStack getResultItem(HolderLookup.Provider provider) {\n\t\treturn output.copy();', 'public ItemStack getResultItem(HolderLookup.Provider provider) {\n\t\treturn output.create();')
    s = s.replace('\t@Override\n\tpublic boolean isSpecial() {', '\t@Override\n\tpublic String group() {\n\t\treturn "";\n\t}\n\n\t@Override\n\tpublic boolean showNotification() {\n\t\treturn false;\n\t}\n\n\t@Override\n\tpublic boolean isSpecial() {')
    s = s.replace(f'public static class Serializer implements RecipeSerializer<{cls}> {{\n\t\tpublic static final Serializer INSTANCE = new Serializer();\n', 'public static class Serializer {\n')
    s = s.replace('ItemStack.STRICT_CODEC.fieldOf("output")', 'ItemStackTemplate.CODEC.fieldOf("output")')
    s = s.replace(f'StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);\n',
                  f'StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);\n\t\tpublic static final RecipeSerializer<{cls}> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);\n')
    s = re.sub(r'\n\t\t@Override\n\t\tpublic MapCodec<\w+> codec\(\) \{\n\t\t\treturn CODEC;\n\t\t\}\n', '\n', s)
    s = re.sub(r'\n\t\t@Override\n\t\tpublic StreamCodec<RegistryFriendlyByteBuf, \w+> streamCodec\(\) \{\n\t\t\treturn STREAM_CODEC;\n\t\t\}\n', '', s)
    s = s.replace(f'return new {cls}(ItemStack.STREAM_CODEC.decode(buf), inputs);', f'return new {cls}(ItemStackTemplate.STREAM_CODEC.decode(buf), inputs);')
    s = s.replace('ItemStack.STREAM_CODEC.encode(buf, recipe.getResultItem(null));', 'ItemStackTemplate.STREAM_CODEC.encode(buf, recipe.output);')
    return add_import(s, 'net.minecraft.world.item.ItemStackTemplate')


def trim_items(s, providers):
    """1.21.5: an item provides a trim material through its properties (a component resolved when the registries are
    ready); the item -> material map comes from tools/port_resources_1_21_11.py"""
    m = re.search(r'public class (\w+) extends Item \{', s)
    if not m or m.group(1) not in providers or '.trimMaterial(' in s:
        return s
    material = providers[m.group(1)]
    return s.replace(f'{PKG}.port.platform.Registrar.itemProps()',
                     f'{PKG}.port.platform.Registrar.itemProps().trimMaterial(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.TRIM_MATERIAL, net.minecraft.resources.Identifier.parse("{material}")))', 1)


def trim_providers():
    """item class -> trim material, from the registrations of TheDeepVoidModItems"""
    items = open(ROOT + 'init/TheDeepVoidModItems.java', encoding='utf-8').read()
    classes = dict(re.findall(r'REGISTRY\.register\("(\w+)", \(\) -> new (\w+)\(\)\)', items))
    materials = json.load(open('common/src/main/resources/the_deep_void_trim_materials.json'))
    return {classes[item.split(':')[1]]: material for item, material in materials.items() if item.split(':')[1] in classes}


def remainders(s):
    """26.1: crafting remainders are item stack templates; NeoForge asks getCraftingRemainder(ItemInstance), Fabric's
    FabricItem getCraftingRemainder(ItemStack)"""
    m = re.search(r'\t/\*\* NeoForge IItemExtension \*/\n\tpublic ItemStack getCraftingRemainder\(ItemStack itemstack\) \{\n\t\treturn new ItemStack\(([\w.]+)\);\n\t\}\n\n'
                  r'\t/\*\* Fabric FabricItem \*/\n\tpublic ItemStack getRecipeRemainder\(ItemStack itemstack\) \{\n\t\treturn getCraftingRemainder\(itemstack\);\n\t\}\n', s)
    if not m:
        return s
    item = m.group(1)
    new = (f'\t/** NeoForge IItemExtension */\n\tpublic net.minecraft.world.item.ItemStackTemplate getCraftingRemainder(net.minecraft.world.item.ItemInstance instance) {{\n\t\treturn new net.minecraft.world.item.ItemStackTemplate({item});\n\t}}\n\n'
           f'\t/** Fabric FabricItem */\n\tpublic net.minecraft.world.item.ItemStackTemplate getCraftingRemainder(ItemStack itemstack) {{\n\t\treturn new net.minecraft.world.item.ItemStackTemplate({item});\n\t}}\n')
    return s[:m.start()] + new + s[m.end():]


def fluid_types(s):
    """26.1: the fluid type's client extensions have no textures (fluid models, the NeoForge module registers them)"""
    return re.sub(r'\n\t\t\t@Override\n\t\t\tpublic Identifier get(?:Still|Flowing)Texture\(\) \{\n\t\t\t\treturn \w+;\n\t\t\t\}\n', '\n', s)


def port(s, providers):
    s = entities(s)
    s = blocks(s)
    s = render(s)
    s = screens(s)
    s = jei_recipe(s)
    s = trim_items(s, providers)
    s = fluid_types(s)
    s = remainders(s)
    return s


def main():
    providers = trim_providers()
    changed = 0
    neoforge_only = ['neoforge/src/main/java/' + line.split(':', 1)[1].strip() for line in open('tools/manual-files.txt') if line.startswith('neoforge:')]
    for path in glob.glob(ROOT + '**/*.java', recursive=True) + neoforge_only:
        s = open(path, encoding='utf-8').read()
        o = port(s, providers)
        if o != s:
            open(path, 'w', encoding='utf-8', newline='\n').write(o)
            changed += 1
    print('changed', changed, '; trim material items', len(providers))


if __name__ == '__main__':
    main()
