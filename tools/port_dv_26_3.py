#!/usr/bin/env python3
"""The Deep Void 26.2 -> 26.3, the mod's own rules (idempotent; repo root). Input (GLFW -> SDL) is
tools/port_263_input.py (modding-kb scripts/port_263_input.py)."""
import glob
import re

ROOTS = ['common/src/main/java/', 'neoforge/src/main/java/', 'fabric/src/main/java/']
PKG = 'net.sombrius.thedeepvoid'
ROOT_JAVA = 'common/src/main/java/net/sombrius/thedeepvoid/'
FEATURES = ROOT_JAVA + 'world/features/'
DATA = 'common/src/main/resources/data/the_deep_void/'

PUSH_REACTIONS = {'NORMAL': 'PUSH_PULL', 'PUSH_ONLY': 'PUSH', 'DESTROY': 'POPPED', 'BLOCK': 'IMMOVEABLE', 'IGNORE': 'IGNORE_ENTITY'}
TOOLS = {'Axe': 'axe', 'Shovel': 'shovel', 'Hoe': 'hoe'}
TOOL_TAGS = {'Axe': 'AXES', 'Shovel': 'SHOVELS', 'Hoe': 'HOES'}


def entities(s):
    # MCreator's command sources: the names come from the entity, or a component (no text name, no entity)
    s = re.sub(r'(new CommandSourceStack\([^;]*?),\s*"",\s*(Component\.literal\(""\)),\s*([^;]*?),\s*null\)', r'\1, \2, \3)', s)
    s = re.sub(r'(new CommandSourceStack\([^;]*?LevelBasedPermissionSet\.\w+),\s*(\w+)\.getName\(\)\.getString\(\),\s*\2\.getDisplayName\(\),\s*([^;]*?),\s*\2\)', r'\1, \3, \2)', s)
    # 26.3: the swing is a SwingState ticked by LivingEntity itself; it takes the swing animation
    s = re.sub(r'\n\t+this\.updateSwingTime\(\);', '', s)
    s = re.sub(r'\bgetAttackAnim\(', 'getSwingAnimation(', s)
    s = re.sub(r'\.swing\((InteractionHand\.\w+), (true|false)\)', r'.swing(\1, net.minecraft.world.item.component.SwingAnimation.DEFAULT, \2)', s)
    s = re.sub(r'\.setInvulnerable\(', '.setPermanentlyInvulnerable(', s)
    # 26.3: dropping and giving back items name their prediction (the server does it here)
    s = re.sub(r'(\b(?:playerIn|player)\.drop\([^;]*?, (?:true|false))\);', r'\1, net.minecraft.util.Prediction.SERVER_ONLY);', s)
    s = re.sub(r'(\.placeItemBackInInventory\((?:[^;,()]|\((?:[^()]|\([^()]*\))*\))*?)\);', r'\1, net.minecraft.util.Prediction.SERVER_ONLY);', s)
    # 26.3: counting or clearing the inventory is a flag (MCreator removes)
    s = re.sub(r'\.clearOrCountMatchingItems\(([^;]*?)(?<!false)(?<!true), (\d+), (_player\.inventoryMenu\.getCraftSlots\(\))\)', r'.clearOrCountMatchingItems(\1, false, \2, \3)', s)
    s = s.replace('.getStructureManager()', '.getStructureTemplateManager()')
    return s


def blocks(s):
    # 26.3: bone meal knows who applies it (MCreator's blocks: any)
    s = re.sub(r'public (boolean isBonemealSuccess\(Level \w+, RandomSource \w+, BlockPos \w+, BlockState \w+)\)', r'public \1, net.minecraft.world.level.block.BonemealSource source)', s)
    s = re.sub(r'public (boolean isValidBonemealTarget\(LevelReader \w+, BlockPos \w+, BlockState \w+)\)', r'public \1, net.minecraft.world.level.block.BonemealSource source)', s)
    s = re.sub(r'public (void performBonemeal\(ServerLevel \w+, RandomSource \w+, BlockPos \w+, BlockState \w+)\)', r'public \1, net.minecraft.world.level.block.BonemealSource source)', s)
    for a, b in PUSH_REACTIONS.items():
        s = re.sub(r'\bPushReaction\.' + a + r'\b', 'PushReaction.' + b, s)
    return s


def tools(s):
    """26.3: no AxeItem/ShovelItem/HoeItem: tools are items with the tool's properties (which set the block transformer)"""
    m = re.search(r'extends (Axe|Shovel|Hoe)Item \{', s)
    if m:
        kind = m.group(1)
        s = s.replace(m.group(0), 'extends Item {')
        s = re.sub(r'super\(TOOL_TIER, ([^,]+), ([^,]+), (' + re.escape(PKG) + r'\.port\.platform\.Registrar\.itemProps\(\)[^;]*)\);',
                   lambda t: f'super({t.group(3)}.{TOOLS[kind]}(TOOL_TIER, {t.group(1)}, {t.group(2)}));', s)
    for kind, tag in TOOL_TAGS.items():
        s = re.sub(r'(\((?:[^()]|\([^()]*\))*\)|\b\w+)\.getItem\(\) instanceof ' + kind + r'Item\b', r'\1.is(net.minecraft.tags.ItemTags.' + tag + ')', s)
        s = s.replace(f'import net.minecraft.world.item.{kind}Item;\n', '')
    return s


def render(s):
    s = re.sub(r'\.mulPose\((Axis\.\w+\.rotation(?:Degrees)?\()', r'.rotate(\1', s)
    # 26.3: no crumbling overlay argument
    s = re.sub(r'(\.submitModel(?:Part)?\((?:[^()]|\((?:[^()]|\([^()]*\))*\))*?), null\)', r'\1)', s)
    return s


def hands(s):
    """26.3: the first person hand heights are the local player's (FirstPersonHandsAndItems; the AT/AW open them)"""
    s = s.replace('ItemInHandRenderer renderer = Minecraft.getInstance().gameRenderer.itemInHandRenderer;',
                  'net.minecraft.client.player.FirstPersonHandsAndItems renderer = Minecraft.getInstance().player == null ? null : Minecraft.getInstance().player.firstPersonHandsAndItems();')
    s = s.replace('import net.minecraft.client.renderer.ItemInHandRenderer;\n', '')
    return s


COMPOST_CHANCES = {'0.3f': 'LOW', '0.5f': 'LOW_MEDIUM', '0.65f': 'MEDIUM', '0.85f': 'MEDIUM_HIGH', '1f': 'ALWAYS_ADD_ONE'}


def compostables():
    """26.3: no ComposterBlock.COMPOSTABLES: the chance is the item's compostable component, set by its properties"""
    path = ROOT_JAVA + 'init/TheDeepVoidModCompostableItems.java'
    s = open(path, encoding='utf-8').read()
    puts = re.findall(r'\t\tComposterBlock\.COMPOSTABLES\.put\(TheDeepVoidModItems\.(\w+)\.get\(\), ([\d.]+f)\);\n', s)
    if not puts:
        return 0
    items = open(ROOT_JAVA + 'init/TheDeepVoidModItems.java', encoding='utf-8').read()
    for const, chance in puts:
        cls = re.search(r'\b' + const + r' = REGISTRY\.register\("\w+", \(\) -> new (\w+)\(\)\);', items).group(1)
        item = ROOT_JAVA + 'item/' + cls + '.java'
        t = open(item, encoding='utf-8').read()
        t = t.replace(PKG + '.port.platform.Registrar.itemProps()', PKG + '.port.platform.Registrar.itemProps().compostable(net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders.COMPOSTABLE_' + COMPOST_CHANCES[chance] + ')', 1)
        open(item, 'w', encoding='utf-8', newline='\n').write(t)
    s = re.sub(r'\t\tComposterBlock\.COMPOSTABLES\.put\([^;]*\);\n', '', s)
    s = s.replace('\tpublic static void addComposterItems(FMLCommonSetupEvent event) {\n', '\tpublic static void addComposterItems(FMLCommonSetupEvent event) {\n\t\t// 26.3: compostable items have the compostable component (their properties)\n')
    s = s.replace('import net.minecraft.world.level.block.ComposterBlock;\n', '')
    open(path, 'w', encoding='utf-8', newline='\n').write(s)
    return len(puts)


def port(s):
    s = hands(s)
    s = entities(s)
    s = blocks(s)
    s = tools(s)
    s = render(s)
    return s


FEATURE = '''package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import {base_import};
import {condition_import};

/** 26.3: a feature is its own configuration; MCreator's condition before {base}'s placement */
public record {name}({base} feature) implements Feature {{
	public static final MapCodec<{name}> CODEC = {base}.CODEC.xmap({name}::new, {name}::feature);

	@Override
	public MapCodec<{name}> codec() {{
		return CODEC;
	}}

	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {{
		int x = origin.getX();
		int y = origin.getY();
		int z = origin.getZ();
		if (!{condition})
			return false;
		return this.feature.place(world, chunkGenerator, random, origin);
	}}
}}
'''

BASES = {'TreeFeature': 'net.minecraft.world.level.levelgen.feature.TreeFeature',
         'ReplaceBlobsFeature': 'net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature',
         'OreFeature': 'net.minecraft.world.level.levelgen.feature.OreFeature',
         'StructureFeature': PKG + '.world.features.StructureFeature'}


def feature(s):
    """MCreator's features with a condition: the vanilla feature (its codec) wrapped, the condition kept"""
    m = re.search(r'public class (\w+) extends (\w+) \{', s)
    if not m or m.group(2) not in BASES or 'FeaturePlaceContext' not in s:
        return s
    name, base = m.groups()
    cond = re.search(r'if \(!(\w+Procedure\.execute\([^;]*?\))\)\n\t\t\treturn false;', s)
    imp = re.search(r'import (' + re.escape(PKG) + r'\.procedures\.\w+);', s)
    return FEATURE.format(name=name, base=base, base_import=BASES[base], condition=cond.group(1), condition_import=imp.group(1))


STRUCTURE_FEATURE = '''package net.sombrius.thedeepvoid.world.features;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.port.platform.Registrar;
import net.sombrius.thedeepvoid.port.platform.RegistryEntry;

/** MCreator's structure feature: a template placed at the origin (26.3: the feature is its configuration) */
public record StructureFeature(Identifier structure, boolean randomRotation, boolean randomMirror, HolderSet<Block> ignoredBlocks, Vec3i offset) implements Feature {
	public static final Registrar<MapCodec<? extends Feature>> REGISTRY = Registrar.create(Registries.FEATURE_TYPE, TheDeepVoidMod.MODID);
	public static final MapCodec<StructureFeature> CODEC = RecordCodecBuilder.mapCodec(builder -> builder
			.group(Identifier.CODEC.fieldOf("structure").forGetter(StructureFeature::structure), com.mojang.serialization.Codec.BOOL.fieldOf("random_rotation").orElse(false).forGetter(StructureFeature::randomRotation),
					com.mojang.serialization.Codec.BOOL.fieldOf("random_mirror").orElse(false).forGetter(StructureFeature::randomMirror),
					RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("ignored_blocks").forGetter(StructureFeature::ignoredBlocks),
					Vec3i.offsetCodec(48).optionalFieldOf("offset", Vec3i.ZERO).forGetter(StructureFeature::offset))
			.apply(builder, StructureFeature::new));
	public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<StructureFeature>> STRUCTURE_FEATURE = REGISTRY.register("structure_feature", () -> CODEC);

	@Override
	public MapCodec<StructureFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel worldGenLevel, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		Rotation rotation = this.randomRotation ? Rotation.getRandom(random) : Rotation.NONE;
		Mirror mirror = this.randomMirror ? Mirror.values()[random.nextInt(2)] : Mirror.NONE;
		BlockPos placePos = origin.offset(this.offset);
		StructureTemplateManager structureManager = worldGenLevel.getLevel().getServer().getStructureTemplateManager();
		StructureTemplate template = structureManager.getOrCreate(this.structure);
		StructurePlaceSettings placeSettings = (new StructurePlaceSettings()).setRotation(rotation).setMirror(mirror).setRandom(random).setIgnoreEntities(false)
				.addProcessor(new BlockIgnoreProcessor(this.ignoredBlocks.stream().map(Holder::value).toList()));
		// A feature may only write into its own chunk and the neighbouring ones; clip the template to that area, one
		// block inside (placing reads the neighbours of the edge blocks)
		ChunkPos chunk = ChunkPos.containing(origin);
		placeSettings.setBoundingBox(new BoundingBox(chunk.getMinBlockX() - 15, worldGenLevel.getMinY(), chunk.getMinBlockZ() - 15, chunk.getMaxBlockX() + 15,
				worldGenLevel.getMaxY(), chunk.getMaxBlockZ() + 15));
		template.placeInWorld(worldGenLevel, placePos, placePos, placeSettings, random, 4);
		return true;
	}
}
'''


def feature_registry(s):
    """26.3: feature types are codecs (worldgen/feature_type); the features themselves are data"""
    if 'public class TheDeepVoidModFeatures {' not in s:
        return s
    s = s.replace('public static final Registrar<Feature<?>> REGISTRY = Registrar.create(Registries.FEATURE, TheDeepVoidMod.MODID);',
                  'public static final Registrar<MapCodec<? extends Feature>> REGISTRY = Registrar.create(Registries.FEATURE_TYPE, TheDeepVoidMod.MODID);')
    s = re.sub(r'public static final RegistryEntry<Feature<\?>, Feature<\?>> (\w+) = REGISTRY\.register\("(\w+)", (\w+)::new\);',
               r'public static final RegistryEntry<MapCodec<? extends Feature>, MapCodec<\3>> \1 = REGISTRY.register("\2", () -> \3.CODEC);', s)
    if 'import com.mojang.serialization.MapCodec;' not in s:
        s = s.replace('import net.minecraft.core.registries.Registries;', 'import com.mojang.serialization.MapCodec;\nimport net.minecraft.core.registries.Registries;')
    return s


def features():
    changed = 0
    for path in glob.glob(FEATURES + '*.java'):
        s = open(path, encoding='utf-8').read()
        o = STRUCTURE_FEATURE if path.endswith('/StructureFeature.java') else feature(s)
        if o != s:
            open(path, 'w', encoding='utf-8', newline='\n').write(o)
            changed += 1
    import os
    config = FEATURES + 'configurations/StructureFeatureConfiguration.java'
    if os.path.exists(config):
        os.remove(config)
        os.rmdir(FEATURES + 'configurations')
        changed += 1
    path = 'common/src/main/java/net/sombrius/thedeepvoid/init/TheDeepVoidModFeatures.java'
    s = open(path, encoding='utf-8').read()
    o = feature_registry(s)
    if o != s:
        open(path, 'w', encoding='utf-8', newline='\n').write(o)
        changed += 1
    return changed


BOTTLES = ('POTION', 'SPLASH_POTION', 'LINGERING_POTION')


def brewing():
    """26.3: brewing recipes are data (minecraft:brewing; RegisterBrewingRecipesEvent and IBrewingRecipe are gone):
    MCreator's brewing recipe classes become recipe files, one per bottle for the potion recipes (as vanilla's)"""
    import json
    import os
    folder = ROOT_JAVA + 'recipes/brewing/'
    if not os.path.isdir(folder):
        return 0
    items = dict(re.findall(r'\b(\w+) = REGISTRY\.register\("(\w+)"', open(ROOT_JAVA + 'init/TheDeepVoidModItems.java', encoding='utf-8').read()))
    potions = dict(re.findall(r'\b(\w+) = REGISTRY\.register\("(\w+)"', open(ROOT_JAVA + 'init/TheDeepVoidModPotions.java', encoding='utf-8').read()))
    blocks = dict(re.findall(r'\b(\w+) = REGISTRY\.register\("(\w+)"', open(ROOT_JAVA + 'init/TheDeepVoidModBlocks.java', encoding='utf-8').read()))

    def item(expr):
        expr = expr.strip()
        m = re.fullmatch(r'TheDeepVoidModItems\.(\w+)\.get\(\)', expr)
        if m:
            return 'the_deep_void:' + items[m.group(1)]
        m = re.fullmatch(r'TheDeepVoidModBlocks\.(\w+)\.get\(\)', expr)
        if m:
            return 'the_deep_void:' + blocks[m.group(1)]
        m = re.fullmatch(r'(?:Items|Blocks)\.(\w+)', expr)
        if m:
            return 'minecraft:' + m.group(1).lower()
        m = re.search(r'Identifier\.parse\("([\w:/]+)"\)', expr)
        if m and 'TagKey' in expr:
            return '#' + m.group(1)
        raise SystemExit('brewing: ' + expr)

    def potion(expr):
        m = re.fullmatch(r'TheDeepVoidModPotions\.(\w+)\.holder\(\)', expr.strip())
        if m:
            return 'the_deep_void:' + potions[m.group(1)]
        return 'minecraft:' + re.fullmatch(r'Potions\.(\w+)', expr.strip()).group(1).lower()

    written = 0
    for path in sorted(glob.glob(folder + '*.java')):
        s = open(path, encoding='utf-8').read()
        name = re.sub(r'(?<!^)(?=[A-Z])', '_', re.search(r'public class (\w+)BrewingRecipe ', s).group(1)).lower()
        reagent = {'item': item(re.search(r'return Ingredient\.of\((.*)\)\.test\(ingredient\);', s).group(1))}
        out = re.search(r'if \(isInput\(input\) && isIngredient\(ingredient\)\) \{\n\t+return (.*);\n', s).group(1)
        simple = re.search(r'return Ingredient\.of\((.*)\)\.test\(input\);', s)
        if simple:
            inputs = [(None, {'item': item(simple.group(1))})]
        else:
            bottles = re.findall(r'inputItem == Items\.(\w+)', s)
            p = re.search(r'(?:optionalPotion\.get\(\)|PotionContents\.EMPTY\))\.is\((TheDeepVoidModPotions\.\w+\.holder\(\)|Potions\.\w+)\)', s).group(1)
            inputs = [(b, {'item': 'minecraft:' + b.lower(), 'potion_contents': {'potions': potion(p)}}) for b in bottles]
        for bottle, given in inputs:
            m = re.fullmatch(r'new ItemStack\((.*)\)', out)
            if m:
                result = {'id': item(m.group(1))}
            else:
                m = re.fullmatch(r'PotionContents\.createItemStack\((.*?), (.*)\)', out)
                result_item = 'minecraft:' + bottle.lower() if m.group(1) == 'input.getItem()' else item(m.group(1))
                result = {'components': {'minecraft:potion_contents': {'potion': potion(m.group(2))}}, 'id': result_item}
            recipe = {'type': 'minecraft:brewing', 'input': given, 'output': result, 'reagent': reagent}
            target = DATA + 'recipe/brewing/' + name + ('' if bottle in (None, 'POTION') or len(inputs) == 1 else '_' + bottle.lower()) + '.json'
            os.makedirs(os.path.dirname(target), exist_ok=True)
            with open(target, 'w', encoding='utf-8', newline='\n') as f:
                f.write(json.dumps(recipe, indent=2) + '\n')
            written += 1
        os.remove(path)
    os.rmdir(folder)
    if not os.listdir(ROOT_JAVA + 'recipes'):
        os.rmdir(ROOT_JAVA + 'recipes')
    return written


def fuels():
    """26.3: fuel is the item's cooking_fuel component (FurnaceFuelBurnTimeEvent and Fabric's FuelValueEvents are gone):
    a burn time provider as vanilla's (shorter in the fast furnaces), set by the item's properties"""
    import json
    import os
    path = ROOT_JAVA + 'init/TheDeepVoidModFuels.java'
    if not os.path.exists(path):
        return 0
    s = open(path, encoding='utf-8').read()
    fuels = re.findall(r'if \(itemstack\.getItem\(\) == TheDeepVoidModItems\.(\w+)\.get\(\)\)\n\t+event\.setBurnTime\((\d+)\);', s)
    items = open(ROOT_JAVA + 'init/TheDeepVoidModItems.java', encoding='utf-8').read()
    for const, ticks in fuels:
        name, cls = re.search(r'\b' + const + r' = REGISTRY\.register\("(\w+)", \(\) -> new (\w+)\(\)\);', items).groups()
        provider = {'type': 'minecraft:div', 'left': int(ticks), 'right': {'type': 'minecraft:conditional', 'condition': 'minecraft:block/fast_cooking',
                    'on_false': 'minecraft:cooking/normal_burn_time_reduction_factor', 'on_true': 'minecraft:cooking/fast_burn_time_reduction_factor'}}
        target = DATA + 'context_int_provider/cooking/time_' + name + '.json'
        os.makedirs(os.path.dirname(target), exist_ok=True)
        with open(target, 'w', encoding='utf-8', newline='\n') as f:
            f.write(json.dumps(provider, indent=2) + '\n')
        item = ROOT_JAVA + 'item/' + cls + '.java'
        t = open(item, encoding='utf-8').read()
        t = t.replace(PKG + '.port.platform.Registrar.itemProps()', PKG + '.port.platform.Registrar.itemProps().cookingFuel(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.CONTEXT_INT_PROVIDER, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "cooking/time_' + name + '")))', 1)
        open(item, 'w', encoding='utf-8', newline='\n').write(t)
    os.remove(path)
    return len(fuels)


def main():
    changed = 0
    for root in ROOTS:
        for path in glob.glob(root + '**/*.java', recursive=True):
            s = open(path, encoding='utf-8').read()
            o = port(s)
            if o != s:
                open(path, 'w', encoding='utf-8', newline='\n').write(o)
                changed += 1
    print('changed', changed, '; features', features(), '; compostables', compostables(), '; brewing recipes', brewing(), '; fuels', fuels())


if __name__ == '__main__':
    main()
