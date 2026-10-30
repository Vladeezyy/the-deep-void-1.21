#!/usr/bin/env python3
"""NeoForge 1.21.1 -> 1.21.11: The Deep Void's own rules (after tools/port_1_21_11.py, the MCreator rules from The
Midnight Lurker). Idempotent, run from the repo root: python3 -I tools/port_dv_1_21_11.py

Items:
- 1.21.2: items and blocks carry their id in their properties: new Item.Properties() / BlockBehaviour.Properties.of()
  -> Registrar.itemProps() / blockProps() (the id of the entry being registered); block items keep the block.* names.
- 1.21.2: Tier -> the ToolMaterial record; the repair ingredient is an item tag (data/the_deep_void/tags/item/
  tool_materials/<repair items>.json, written here).
- 1.21.5: SwordItem / PickaxeItem / DiggerItem are gone: plain Item with Item.Properties.sword() / pickaxe(); the axe,
  shovel and hoe keep their classes with the material in the constructor.
- 1.21.2: use() returns InteractionResult (InteractionResultHolder is gone).
- 1.21.9: GameProfile is a record (getId() -> id()).
"""
import glob
import json
import os
import re

ROOT = 'common/src/main/java/net/sombrius/thedeepvoid/'
PKG = 'net.sombrius.thedeepvoid'
TAGS = 'common/src/main/resources/data/the_deep_void/tags/item/tool_materials/'
REGISTRAR = f'{PKG}.port.platform.Registrar'


def add_import(s, imp):
    if ('import ' + imp + ';') in s:
        return s
    return re.sub(r'^(package [\w.]+;\n)', r'\1\nimport ' + imp + ';', s, count=1, flags=re.M)


def method_return(body, name):
    m = re.search(r'public [\w<>]+ ' + name + r'\(\) \{\s*return (.*?);\s*\}', body, re.S)
    return m.group(1).strip() if m else None


def item_id(expr):
    """TheDeepVoidModItems.X.get() / Items.X / Blocks.X -> namespaced id"""
    m = re.match(r'(TheDeepVoidModItems|TheDeepVoidModBlocks)\.(\w+)\.get\(\)$', expr)
    if m:
        return 'the_deep_void:' + m.group(2).lower()
    m = re.match(r'(Items|Blocks)\.(\w+)$', expr)
    if m:
        return 'minecraft:' + m.group(2).lower()
    raise SystemExit('unknown repair item ' + expr)


REPAIR_TAGS = {}


def repair_tag(ingredient):
    inner = re.match(r'Ingredient\.of\((.*)\)$', ingredient, re.S).group(1).strip()
    parts = [p.strip() for p in re.split(r',\s*(?=new ItemStack|TheDeepVoidMod|Items\.|Blocks\.)', inner)] if inner else []
    items = [item_id(re.sub(r'^new ItemStack\((.*)\)$', r'\1', p)) for p in parts]
    name = '_'.join(i.split(':')[1] for i in items) or 'none'
    REPAIR_TAGS[name] = items
    return f'net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/{name}"))'


def tiers(s):
    """private static final Tier TOOL_TIER = new Tier() {...}; -> a ToolMaterial with the same values"""
    def repl(m):
        body = m.group(2)
        values = [method_return(body, n) for n in ('getIncorrectBlocksForDrops', 'getUses', 'getSpeed', 'getAttackDamageBonus', 'getEnchantmentValue', 'getRepairIngredient')]
        if None in values:
            raise SystemExit('unexpected Tier in ' + m.group(0)[:200])
        incorrect, uses, speed, bonus, enchant, ingredient = values
        return f'{m.group(1)}ToolMaterial TOOL_TIER = new ToolMaterial({incorrect}, {uses}, {speed}f, {bonus}f, {enchant}, {repair_tag(ingredient)});'
    s = re.sub(r'(\n\tprivate static final |\n\t\tprivate static final |\n\tpublic static final )Tier TOOL_TIER = new Tier\(\) \{(.*?)\n\t+\};', repl, s, flags=re.S)
    s = re.sub(r'(\d)ff\b', r'\1f', s)
    if 'ToolMaterial TOOL_TIER' in s:
        s = add_import(s, 'net.minecraft.world.item.ToolMaterial')
        s = s.replace('import net.minecraft.world.item.Tier;\n', '')
    return s


TOOLS = {'SwordItem': 'sword', 'PickaxeItem': 'pickaxe'}
KEEP = {'AxeItem', 'ShovelItem', 'HoeItem'}


def tools(s):
    m = re.search(r'public (?:abstract )?class \w+ extends (SwordItem|PickaxeItem|AxeItem|ShovelItem|HoeItem)\b', s)
    if not m:
        return s
    kind = m.group(1)
    attrs = r'(?:net\.sombrius\.thedeepvoid\.port\.PortUtil\.swordAttributes|(?:Sword|Digger|Pickaxe|Axe|Shovel|Hoe)Item\.createAttributes)\(TOOL_TIER, ([-\d.]+f?), ([-\d.]+f?)\)'
    if kind in TOOLS:
        s = re.sub(r'super\(TOOL_TIER, (new Item\.Properties\(\)|' + re.escape(REGISTRAR) + r'\.itemProps\(\))((?:\.\w+\([^()]*\))*)\.attributes\(' + attrs + r'\)',
                   lambda x: f'super({REGISTRAR}.itemProps(){x.group(2)}.{TOOLS[kind]}(TOOL_TIER, {x.group(3)}, {x.group(4)})', s)
        s = re.sub(r'(public (?:abstract )?class \w+ extends )' + kind + r'\b', r'\1Item', s)
    else:
        s = re.sub(r'super\(TOOL_TIER, (new Item\.Properties\(\)|' + re.escape(REGISTRAR) + r'\.itemProps\(\))((?:\.\w+\([^()]*\))*)\.attributes\(' + attrs + r'\)',
                   lambda x: f'super(TOOL_TIER, {x.group(3)}, {x.group(4)}, {REGISTRAR}.itemProps(){x.group(2)}', s)
    s = re.sub(r'import net\.minecraft\.world\.item\.(SwordItem|PickaxeItem|DiggerItem);\n', '', s)
    return s


def use(s):
    s = s.replace('public InteractionResultHolder<ItemStack> use(', 'public InteractionResult use(')
    s = s.replace('InteractionResultHolder<ItemStack> ar = ', 'InteractionResult ar = ')
    s = re.sub(r'InteractionResultHolder\.(success|fail|pass|consume)\([^;]*?\)(?=;)', lambda m: 'InteractionResult.' + m.group(1).upper(), s)
    s = re.sub(r'InteractionResultHolder\.sidedSuccess\([^;]*?\)(?=;)', 'InteractionResult.SUCCESS', s)
    # the used stack (super.use() keeps it in the hand)
    s = s.replace('ar.getObject()', 'entity.getItemInHand(hand)')
    if 'InteractionResult' in s:
        s = s.replace('import net.minecraft.world.InteractionResultHolder;\n', '')
        s = add_import(s, 'net.minecraft.world.InteractionResult')
    return s


def properties(s):
    if 'public final class Registrar' in s or 'public class Registrar' in s:
        return s
    s = s.replace('new Item.Properties()', f'{REGISTRAR}.itemProps()')
    s = s.replace('BlockBehaviour.Properties.of()', f'{REGISTRAR}.blockProps()')
    s = s.replace('BlockBehaviour.Properties.ofFullCopy(', f'{REGISTRAR}.blockCopy(')
    s = s.replace('BlockBehaviour.Properties.ofLegacyCopy(', f'{REGISTRAR}.blockLegacyCopy(')
    # block items are named after their block (block.the_deep_void.x)
    s = re.sub(r'(new (?:BlockItem|DoubleHighBlockItem)\([^;]*?' + re.escape(REGISTRAR) + r'\.itemProps\(\))(?!\.useBlockDescriptionPrefix)', r'\1.useBlockDescriptionPrefix()', s)
    return s


def items2(s):
    # 1.21.2: cooldowns per stack (the cooldown group of its item)
    s = re.sub(r'\b(addCooldown|getCooldownPercent|isOnCooldown)\((.+?)\.getItem\(\)(?=[,)])', r'\1(\2', s)
    s = re.sub(r'\b(addCooldown|getCooldownPercent|isOnCooldown)\((TheDeepVoidModItems\.\w+\.get\(\))(?=[,)])', r'\1(new ItemStack(\2)', s)
    # 1.21.2: Ingredient.of(ItemLike...)
    s = re.sub(r'new ItemStack\(((?:TheDeepVoidModItems|TheDeepVoidModBlocks)\.\w+\.get\(\)|Items\.\w+|Blocks\.\w+)\)(?=(?:, new ItemStack\([^()]*(?:\(\))?\))*\))',
               lambda m: m.group(1) if re.search(r'Ingredient\.of\([^;]*$', s[:m.start()].rsplit('\n', 1)[-1]) else m.group(0), s)
    # 1.21.5: tooltips go to a consumer
    s = s.replace('public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {\n\t\tsuper.appendHoverText(itemstack, context, list, flag);',
                  'public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {\n\t\tsuper.appendHoverText(itemstack, context, tooltipDisplay, list, flag);')
    if 'java.util.function.Consumer<Component> list' in s:
        s = re.sub(r'\blist\.add\(', 'list.accept(', s)
    # 1.21.5: inventoryTick runs on the server with the equipment slot (null: elsewhere in the inventory)
    s = re.sub(r'public void inventoryTick\(ItemStack itemstack, Level world, Entity (\w+), int slot, boolean selected\) \{\n(\t+)super\.inventoryTick\(itemstack, world, \1, slot, selected\);',
               r'public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity \1, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {\n\2super.inventoryTick(itemstack, world, \1, equipmentSlot);\n'
               r'\2boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;\n\2int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();', s)
    if 'ServerLevel world, Entity' in s:
        s = add_import(s, 'net.minecraft.server.level.ServerLevel')
        s = add_import(s, 'javax.annotation.Nullable')
    # 1.21.5: a spawn egg is a component of the item properties (the textures replace the colors)
    s = re.sub(r'new SpawnEggItem\(([\w.]+\.get\(\)), -?\d+, -?\d+, (' + re.escape(REGISTRAR) + r'\.itemProps\(\))', r'new SpawnEggItem(\2.spawnEgg(\1)', s)
    # 1.21.9: no getServer() on entities, the player's server is private
    s = re.sub(r'\b(_ent|entity|sourceentity|_player|player|p)\.getServer\(\)', r'\1.level().getServer()', s)
    s = re.sub(r'\b(_player|player|_serverPlayer|serverPlayer|_plr\d*|_sp\d*)\.server\b(?!\.level)', r'\1.level().getServer()', s)
    # 1.21.2: Registry access
    s = s.replace('.registryOrThrow(', '.lookupOrThrow(')
    s = s.replace('.getHolderOrThrow(', '.getOrThrow(')
    # 1.21.9: modifier keys on Minecraft
    s = re.sub(r'\b(?:Screen\.)?hasShiftDown\(\)', 'net.minecraft.client.Minecraft.getInstance().hasShiftDown()', s)
    s = s.replace('net.minecraft.client.Minecraft.getInstance().net.minecraft.client.Minecraft.getInstance().hasShiftDown()', 'net.minecraft.client.Minecraft.getInstance().hasShiftDown()')
    s = re.sub(r'\bThrownPotion\b', 'AbstractThrownPotion', s)
    s = s.replace('import net.minecraft.world.entity.projectile.AbstractThrownPotion;', 'import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;')
    s = s.replace('.noCollission()', '.noCollision()')
    return s


ARMOR_ASSETS = {}


def armor(s):
    """1.21.2: ArmorMaterial is a record with an equipment asset (assets/<ns>/equipment/<name>.json, written by the
    resource converter from tools/armor_assets.json), the armor pieces are plain items with humanoidArmor()."""
    m = re.search(r'\n\tpublic static Holder<ArmorMaterial> ARMOR_MATERIAL = null;\n', s)
    if m:
        reg = re.search(r'\n\t@SubscribeEvent\n\tpublic static void registerArmorMaterial\(RegisterEvent event\) \{\n(.*?)\n\t\}\n', s, re.S)
        body = reg.group(1)
        defense = dict(re.findall(r'map\.put\(ArmorItem\.Type\.(\w+), (\d+)\);', body))
        mat = re.search(r'\}\), (\d+), (.*?), \(\) -> (Ingredient\.of\(.*?\)),\s*List\.of\(new ArmorMaterial\.Layer\(Identifier\.parse\("([^"]+)"\)\)\), ([\d.]+f), ([\d.]+f)\);', body, re.S)
        enchant, sound, ingredient, layer, tough, kb = mat.groups()
        name = re.search(r'registerHelper\.register\(Identifier\.parse\("the_deep_void:(\w+)"\)', body).group(1)
        dur = re.search(r'ArmorItem\.Type\.\w+\.getDurability\((\d+)\)', s)
        durability = dur.group(1) if dur else '0'
        ARMOR_ASSETS[name] = layer
        defense_map = 'Map.of(' + ', '.join(f'ArmorType.{k}, {v}' for k, v in defense.items()) + ')'
        decl = (f'\n\tpublic static final ArmorMaterial ARMOR_MATERIAL = new ArmorMaterial({durability}, {defense_map}, {enchant}, {sound}, {tough}, {kb}, {repair_tag(ingredient)},\n'
                f'\t\t\tResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath("the_deep_void", "{name}")));\n')
        s = s[:m.start()] + decl + s[m.end():]
        s = s.replace(reg.group(0), '\n')
        s = re.sub(r'(public (?:abstract )?class \w+ extends )ArmorItem\b', r'\1Item', s)
        s = re.sub(r'\n\t\tsuper\(ARMOR_MATERIAL, type, properties\);', '\n\t\tsuper(properties.humanoidArmor(ARMOR_MATERIAL, type));', s)
        for imp in ('net.minecraft.world.item.equipment.ArmorMaterial', 'net.minecraft.world.item.equipment.ArmorType', 'net.minecraft.world.item.equipment.EquipmentAssets',
                    'net.minecraft.resources.ResourceKey', 'java.util.Map'):
            s = add_import(s, imp)
        s = s.replace('import net.minecraft.world.item.ArmorMaterial;\n', '').replace('import net.minecraft.world.item.ArmorItem;\n', '')
    s = re.sub(r'\.durability\(ArmorItem\.Type\.\w+\.getDurability\(\d+\)\)', '', s)
    s = re.sub(r'\bArmorItem\.Type\b', 'ArmorType', s)
    if 'ArmorType' in s:
        s = add_import(s, 'net.minecraft.world.item.equipment.ArmorType')
    # NeoForge 21.11's armor texture hook has other parameters: kept as the mod's own helper (the loaders call it)
    s = s.replace('getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel)',
                  'getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, Object layer, boolean innerModel)')
    # 1.21.5: no armor slot list
    s = re.sub(r'Iterables\.contains\((\w+)\.getArmorSlots\(\), (\w+)\)', rf'{PKG}.port.PortUtil.wearing(\1, \2)', s)
    s = re.sub(r'(\w+) instanceof (\w+) armor && armor\.getType\(\) == ArmorType\.(\w+)', rf'\1 instanceof \2 && {PKG}.port.PortUtil.armorType(\1) == ArmorType.\3', s)
    return s


def method_body(s, start):
    """Index of the closing brace of the method whose body opens at s[start] ('{')"""
    depth, i = 0, start
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
            while s[i - 1] == '\\':
                i = s.index('"', i + 1)
        i += 1


def items3(s):
    s = re.sub(r'\bUseAnim\b', 'ItemUseAnimation', s)
    s = s.replace('import net.minecraft.world.item.ItemUseAnimation;\n', '').replace('import net.minecraft.world.item.UseAnim;\n', '')
    if 'ItemUseAnimation' in s:
        s = add_import(s, 'net.minecraft.world.item.ItemUseAnimation')
    # 1.21.2: releaseUsing tells whether the use was released (the bows: true)
    pos = 0
    while (m := re.search(r'public void releaseUsing\(ItemStack (\w+), Level (\w+), LivingEntity (\w+), int (\w+)\) \{', s[pos:])):
        start = pos + m.start()
        open_brace = pos + m.end() - 1
        close = method_body(s, open_brace)
        body = s[open_brace:close].replace('\t\treturn;', '\t\treturn true;')
        body = re.sub(r'(\s)return;', r'\1return true;', body)
        s = s[:start] + s[start:open_brace].replace('public void releaseUsing', 'public boolean releaseUsing') + body + '\treturn true;\n\t' + s[close:]
        pos = start + 10
    s = re.sub(r'super\.releaseUsing\((\w+), (\w+), (\w+), (\w+)\);', r'super.releaseUsing(\1, \2, \3, \4);', s)
    s = re.sub(r'LivingEntity\.getSlotForHand\(([^()]*(?:\([^()]*\))?)\)', r'\1.asEquipmentSlot()', s)
    s = re.sub(r'\.getInventory\(\)\.items\b', '.getInventory().getNonEquipmentItems()', s)
    s = s.replace('SwordItem.createAttributes(', f'{PKG}.port.PortUtil.swordAttributes(')
    # 1.21.2: hurtEnemy returns nothing (the item is always damaged by postHurtEnemy)
    s = re.sub(r'public boolean hurtEnemy\(ItemStack (\w+), LivingEntity (\w+), LivingEntity (\w+)\) \{\n(\t+)boolean retval = super\.hurtEnemy\(\1, \2, \3\);',
               r'public void hurtEnemy(ItemStack \1, LivingEntity \2, LivingEntity \3) {\n\4super.hurtEnemy(\1, \2, \3);', s)
    if 'public void hurtEnemy(' in s:
        s = re.sub(r'(public void hurtEnemy\([^)]*\) \{(?:[^{}]|\{(?:[^{}]|\{[^{}]*\})*\})*?)\n\t+return retval;', r'\1', s)
    return s


VI = 'net.minecraft.world.level.storage.ValueInput'
VO = 'net.minecraft.world.level.storage.ValueOutput'


def blocks(s):
    """1.21.2-1.21.9 block signatures, for every block class (not only the ones extending Block)"""
    s = re.sub(r'public BlockState updateShape\(BlockState (\w+), Direction (\w+), BlockState (\w+), LevelAccessor (\w+), BlockPos (\w+), BlockPos (\w+)\)',
               r'public BlockState updateShape(BlockState \1, LevelReader \4, net.minecraft.world.level.ScheduledTickAccess tickAccess, BlockPos \5, Direction \2, BlockPos \6, BlockState \3, net.minecraft.util.RandomSource random)', s)
    if 'ScheduledTickAccess tickAccess' in s:
        s = re.sub(r'super\.updateShape\((\w+), (\w+), (\w+), (\w+), (\w+), (\w+)\)', r'super.updateShape(\1, \4, tickAccess, \5, \2, \6, \3, random)', s)
        s = re.sub(r'\b(world|level)\.scheduleTick\((\w+), Fluids\.WATER, Fluids\.WATER\.getTickDelay\(\1\)\)', r'tickAccess.scheduleTick(\2, Fluids.WATER, Fluids.WATER.getTickDelay(\1))', s)
        s = add_import(s, 'net.minecraft.world.level.LevelReader')
    # neighborChanged: the neighbor's position became its orientation (null: unknown); the old fromPos is the block itself
    s = re.sub(r'public void neighborChanged\(BlockState (\w+), Level (\w+), BlockPos (\w+), Block (\w+), BlockPos (\w+), boolean (\w+)\) \{\n(\t+)super\.neighborChanged\(\1, \2, \3, \4, \5, \6\);',
               r'public void neighborChanged(BlockState \1, Level \2, BlockPos \3, Block \4, @Nullable net.minecraft.world.level.redstone.Orientation orientation, boolean \6) {\n\7super.neighborChanged(\1, \2, \3, \4, orientation, \6);\n\7BlockPos \5 = \3;', s)
    if 'Orientation orientation' in s:
        s = add_import(s, 'javax.annotation.Nullable')
    s = s.replace('public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos)', 'public int getLightBlock(BlockState state)')
    s = s.replace('public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos)', 'public boolean propagatesSkylightDown(BlockState state)')
    s = re.sub(r'public ItemStack getCloneItemStack\(LevelReader (\w+), BlockPos (\w+), BlockState (\w+)\)', r'public ItemStack getCloneItemStack(LevelReader \1, BlockPos \2, BlockState \3, boolean includeData)', s)
    s = re.sub(r'super\.getCloneItemStack\((\w+), (\w+), (\w+)\)', r'super.getCloneItemStack(\1, \2, \3, includeData)', s)
    s = re.sub(r'public void entityInside\(BlockState (\w+), Level (\w+), BlockPos (\w+), Entity (\w+)\) \{\n(\t+)super\.entityInside\(\1, \2, \3, \4\);',
               r'public void entityInside(BlockState \1, Level \2, BlockPos \3, Entity \4, net.minecraft.world.entity.InsideBlockEffectApplier effectApplier, boolean intersects) {\n\5super.entityInside(\1, \2, \3, \4, effectApplier, intersects);', s)
    return s


def saves(s):
    """1.21.6: entities and block entities save to ValueOutput / load from ValueInput (the same keys)"""
    s = s.replace('public void addAdditionalSaveData(CompoundTag compound)', f'public void addAdditionalSaveData({VO} compound)')
    s = s.replace('public void readAdditionalSaveData(CompoundTag compound)', f'public void readAdditionalSaveData({VI} compound)')
    s = re.sub(r'public void loadAdditional\(CompoundTag (\w+), HolderLookup\.Provider \w+\) \{\n(\t+)super\.loadAdditional\(\1, \w+\);',
               rf'public void loadAdditional({VI} \1) {{\n\2super.loadAdditional(\1);', s)
    s = re.sub(r'public void saveAdditional\(CompoundTag (\w+), HolderLookup\.Provider \w+\) \{\n(\t+)super\.saveAdditional\(\1, \w+\);',
               rf'public void saveAdditional({VO} \1) {{\n\2super.saveAdditional(\1);', s)
    s = re.sub(r'ContainerHelper\.(loadAllItems|saveAllItems)\((\w+), ([\w.]+), \w+\)', r'ContainerHelper.\1(\2, \3)', s)
    if VI in s or VO in s:
        s = re.sub(r'\bcompound\.contains\(("[^"]+")\)', rf'{PKG}.port.PortUtil.has(compound, \1)', s)
    return s


def particles(s):
    """1.21.9: particles are SingleQuadParticles with a sprite from the start and a render layer"""
    if 'extends TextureSheetParticle' not in s:
        return s
    s = s.replace('extends TextureSheetParticle', 'extends SingleQuadParticle')
    s = re.sub(r'(SpriteSet spriteSet\) \{\n\t\tsuper\(world, x, y, z)\);', r'\1, spriteSet.first());', s)
    s = re.sub(r'public ParticleRenderType getRenderType\(\) \{\n(\t+)return ParticleRenderType\.PARTICLE_SHEET_(\w+);',
               lambda m: 'public SingleQuadParticle.Layer getLayer() {\n' + m.group(1) + 'return SingleQuadParticle.Layer.' + ('TRANSLUCENT' if m.group(2) == 'TRANSLUCENT' else 'OPAQUE') + ';', s)
    s = s.replace('double xSpeed, double ySpeed, double zSpeed) {', 'double xSpeed, double ySpeed, double zSpeed, net.minecraft.util.RandomSource random) {')
    s = s.replace('this.pickSprite(spriteSet);', 'this.setSprite(spriteSet.get(this.random));')
    s = s.replace('import net.minecraft.client.particle.TextureSheetParticle;', 'import net.minecraft.client.particle.SingleQuadParticle;')
    s = s.replace('import net.minecraft.client.particle.ParticleRenderType;\n', '')
    return add_import(s, 'net.minecraft.client.particle.SingleQuadParticle')


ARMOR_SLOTS = {'0': 'FEET', '1': 'LEGS', '2': 'CHEST', '3': 'HEAD'}


def procs(s):
    """MCreator procedures, entities and blocks: the smaller 1.21.2-1.21.11 API changes"""
    # 1.21.11: command permission levels are permission sets
    if 'new CommandSourceStack(' in s:
        s = re.sub(r'(\(ServerLevel\) \w+(?:\.level\(\))? : null,\s*)4,', r'\1LevelBasedPermissionSet.OWNER,', s)
        if 'LevelBasedPermissionSet.OWNER' in s:
            s = add_import(s, 'net.minecraft.server.permissions.LevelBasedPermissionSet')
    # 1.21.5: no sword and pickaxe classes; the item tags have the mod's tools too
    s = re.sub(r'\.getItem\(\) instanceof SwordItem\b', '.is(net.minecraft.tags.ItemTags.SWORDS)', s)
    s = re.sub(r'\.getItem\(\) instanceof PickaxeItem\b', '.is(net.minecraft.tags.ItemTags.PICKAXES)', s)
    s = re.sub(r'import net\.minecraft\.world\.item\.(SwordItem|PickaxeItem|DiggerItem|TieredItem);\n', '', s)
    # 1.21.2: Registry.get(id) is an Optional holder; getValue(id) the old lookup (calls split over lines)
    s = re.sub(r'\b(BuiltInRegistries\.[A-Z_]+)(\s*)\.get\((\s*)(Identifier\.)', r'\1\2.getValue(\3\4', s)
    s = re.sub(r'\b(BuiltInRegistries\.[A-Z_]+)\.getTag\(', r'\1.get(', s)
    # 1.21.2: arrows
    s = re.sub(r'\.inGround\b(?!\s*=[^=])', '.isInGround()', s)
    s = re.sub(r'InteractionResult\.sidedSuccess\((?:[^()]|\([^()]*\))*\)', 'InteractionResult.SUCCESS', s)
    s = re.sub(r'\bInteractionResult\.CONSUME_PARTIAL\b', 'InteractionResult.CONSUME', s)
    # 1.21.2: the camera's entity
    s = s.replace('getCamera().getEntity()', 'getCamera().entity()')
    # 1.21.2: FastColor.ARGB32 is ARGB
    s = s.replace('FastColor.ARGB32.', 'ARGB.').replace('import net.minecraft.util.FastColor;', 'import net.minecraft.util.ARGB;')
    # 1.21.2: block offsets take the position only
    s = re.sub(r'\.getOffset\((\w+), (\w+)\)', r'.getOffset(\2)', s)
    # 1.21.2: arrows' piercing (opened by the AT), particles, spawned offspring, teleports, kills
    s = re.sub(r'new DustParticleOptions\(new Vector3f\(([^()]*?), ([^()]*?), ([^()]*?)\), ', r'new DustParticleOptions(net.minecraft.util.ARGB.colorFromFloat(1f, \1, \2, \3), ', s)
    s = re.sub(r'(TheDeepVoidModEntities\.\w+\.get\(\)\.create\()(\w+)\)', r'\1\2, EntitySpawnReason.BREEDING)', s)
    s = re.sub(r'\.teleportTo\((\w+), ([^,;]+), ([^,;]+), ([^,;]+), ([^,;]+), ([^,;]+)\);', r'.teleportTo(\1, \2, \3, \4, java.util.Set.of(), \5, \6, true);', s)
    s = re.sub(r'\b(?!super\b)(\w+)\.kill\(\);', rf'{PKG}.port.PortUtil.kill(\1);', s)
    s = s.replace('protected void customServerAiStep() {\n\t\tsuper.customServerAiStep();', 'protected void customServerAiStep(ServerLevel level) {\n\t\tsuper.customServerAiStep(level);')
    if 'customServerAiStep(ServerLevel' in s:
        s = add_import(s, 'net.minecraft.server.level.ServerLevel')
    # 1.21.2: DirectionProperty is an EnumProperty<Direction>
    s = re.sub(r'if \(_prop instanceof EnumProperty _dp\)(\n\t+)return _bs\.getValue\(_dp\);', r'if (_prop instanceof EnumProperty _dp && _dp.getValueClass() == Direction.class)\1return (Direction) _bs.getValue(_dp);', s)
    # 1.21.5: tooltips (the items named the context "level")
    s = s.replace('public void appendHoverText(ItemStack itemstack, Item.TooltipContext level, List<Component> list, TooltipFlag flag) {\n\t\tsuper.appendHoverText(itemstack, level, list, flag);',
                  'public void appendHoverText(ItemStack itemstack, Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {\n\t\tsuper.appendHoverText(itemstack, level, tooltipDisplay, list, flag);')
    if 'java.util.function.Consumer<Component> list' in s:
        s = re.sub(r'\blist\.add\(', 'list.accept(', s)
    # 1.21.5: the armor slots are equipment slots of the entity
    s = re.sub(r'\.getInventory\(\)\.armor\.set\(([0-3]), ', lambda m: f'.setItemSlot(net.minecraft.world.entity.EquipmentSlot.{ARMOR_SLOTS[m.group(1)]}, ', s)
    # 1.21.2: tag ingredients take the holder set
    s = re.sub(r'Ingredient\.of\((net\.minecraft\.tags\.TagKey\.create\(net\.minecraft\.core\.registries\.Registries\.ITEM, Identifier\.parse\("[^"]+"\)\))\)',
               r'Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.getOrThrow(\1))', s)
    # 1.21.9: modifier keys are asked from the client
    s = re.sub(r'\bScreen\.has(Alt|Shift|Control)Down\(\)', r'net.minecraft.client.Minecraft.getInstance().has\1Down()', s)
    # 1.21.6: a server player's level is a ServerLevel
    s = re.sub(r'\.serverLevel\(\)', '.level()', s)
    # 1.21.11: game rules live on the server levels
    s = re.sub(r'world\.getLevelData\(\)\.getGameRules\(\)\.get\(([\w.]+(?:\.get\(\))?)\)', rf'{PKG}.port.PortUtil.rule(world, \1)', s)
    s = s.replace('world.getLevelData().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)', f'{PKG}.port.PortUtil.rule(world, net.minecraft.world.level.gamerules.GameRules.SPAWN_MOBS)')
    # NBT of a block entity through the MCreator helpers (a variable key)
    for name, default in (('getDouble', '0'), ('getBoolean', 'false'), ('getString', '""'), ('getInt', '0')):
        s = re.sub(r'(PortUtil\.data\(\w+\)\.)' + name + r'\((\w+)\)', r'\1' + name + r'Or(\2, ' + default + ')', s)
    # 1.21.5: removed blocks drop their container by the block entity; the block updates the comparators
    s = re.sub(r'\t@Override\n\tpublic void onRemove\(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving\) \{\n\t\tif \(state\.getBlock\(\) != newState\.getBlock\(\)\) \{\n'
               r'\t\t\tBlockEntity blockEntity = world\.getBlockEntity\(pos\);\n\t\t\tif \(blockEntity instanceof \w+ be\) \{\n\t\t\t\tContainers\.dropContents\(world, pos, be\);\n'
               r'\t\t\t\tworld\.updateNeighbourForOutputSignal\(pos, this\);\n\t\t\t\}\n\t\t\tsuper\.onRemove\(state, world, pos, newState, isMoving\);\n\t\t\}\n\t\}\n',
               '\t@Override\n\tprotected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean isMoving) {\n\t\tContainers.updateNeighboursAfterDestroy(state, world, pos);\n\t}\n', s)
    if 'affectNeighborsAfterRemoval(BlockState state, ServerLevel' in s:
        s = add_import(s, 'net.minecraft.server.level.ServerLevel')
    if 'import net.minecraft.world.item.ArmorItem;' in s and not re.search(r'\bArmorItem\b', s.replace('import net.minecraft.world.item.ArmorItem;', '')):
        s = s.replace('import net.minecraft.world.item.ArmorItem;\n', '')
    # JEI 27: brewing recipes have ids
    s = re.sub(r'(brewingRecipes\.add\(factory\.createBrewingRecipe\((?:(?!"brewing_")[^;])*?)\)\);', r'\1, Identifier.fromNamespaceAndPath("the_deep_void", "brewing_" + brewingRecipes.size())));', s)
    s = re.sub(r'\b(BuiltInRegistries\.[A-Z_]+)\.getOrCreateTag\(', r'\1.getOrThrow(', s)
    # NeoForge 21.9+ does not strip @OnlyIn members any more: a tooltip's "entity or the client player" merges Entity
    # and LocalPlayer, which makes the verifier load LocalPlayer on a dedicated server
    s = s.replace('itemstack.getEntityRepresentation() : Minecraft.getInstance().player;', f'itemstack.getEntityRepresentation() : {PKG}.port.PortUtil.clientPlayer.get();')
    s = s.replace('@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) ', '')
    # NeoForge 21.11 uses the vanilla TriState
    s = s.replace('net.neoforged.neoforge.common.util.TriState', 'net.minecraft.util.TriState')
    # smaller renames and signature changes
    s = re.sub(r'\bBlocks\.CHAIN\b', 'Blocks.IRON_CHAIN', s)
    s = re.sub(r'\bItems\.CHAIN\b', 'Items.IRON_CHAIN', s)
    s = re.sub(r'(addParticle|sendParticles)\(ParticleTypes\.FLASH,', r'\1(net.minecraft.core.particles.ColorParticleOption.create(ParticleTypes.FLASH, -1),', s)
    s = s.replace('.getMainCamera().getEntity()', '.getMainCamera().entity()').replace('.getMainCamera().getPosition()', '.getMainCamera().position()')
    s = re.sub(r'\b(\w+)\.getTimer\(\)', r'\1.getDeltaTracker()', s)
    s = s.replace('getMinBuildHeight()', 'getMinY()').replace('getMaxBuildHeight()', 'getMaxY()')
    s = s.replace('(EntityType<T>) entityTypeBuilder.build(registryname)', '(EntityType<T>) entityTypeBuilder.build(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", registryname)))')
    s = s.replace('BlockEntityType.Builder.of(supplier, block.get()).build(null)', 'new BlockEntityType<>(supplier, java.util.Set.of(block.get()))')
    s = re.sub(r'\bthis\.spawnAtLocation\(', f'{PKG}.port.PortUtil.spawnAtLocation(this, ', s)
    s = re.sub(r'itemstack\.getItem\(\) instanceof (\w+) armor && armor\.getType\(\) == ArmorType\.(\w+)', rf'itemstack.getItem() instanceof \1 && {PKG}.port.PortUtil.armorType(itemstack) == ArmorType.\2', s)
    s = re.sub(r'(setRenderLayer\([^;]*?), RenderType\.translucent\(\)\)', r'\1, net.minecraft.client.renderer.chunk.ChunkSectionLayer.TRANSLUCENT)', s)
    if 'TheDeepVoidModItems {' in s:
        s = s.replace('new BlockItem(', f'new {PKG}.port.PortBlockItem(')
    # 1.21.5: blocks have no tooltips; the block item asks the block (port.PortBlockItem)
    if re.search(r'public class \w+ extends Block\b', s) and 'super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);' in s:
        s = re.sub(r'\t@Override\n(\t@net\.neoforged[^\n]*\n\tpublic void appendHoverText\()', r'\1', s)
        s = s.replace('\t\tsuper.appendHoverText(itemstack, context, tooltipDisplay, list, flag);\n', '')
        s = re.sub(r'(public class \w+ extends Block)( implements ([\w, ]+))? \{', lambda m: m.group(1) + ' implements ' + (m.group(3) + ', ' if m.group(3) else '') + f'{PKG}.port.PortBlockItem.Tooltip {{', s, count=1)
    if re.search(r'\bItemStack\b', s) and 'import net.minecraft.world.item.ItemStack;' not in s and 'import net.minecraft.world.item.*;' not in s and 'class ItemStack' not in s:
        s = add_import(s, 'net.minecraft.world.item.ItemStack')
    # 1.21.9: sprite particle registrations moved
    s = s.replace('ParticleEngine.SpriteParticleRegistration', 'ParticleResources.SpriteParticleRegistration')
    s = s.replace('import net.minecraft.client.particle.ParticleEngine;', 'import net.minecraft.client.particle.ParticleResources;')
    if 'EntitySpawnReason.' in s:
        s = add_import(s, 'net.minecraft.world.entity.EntitySpawnReason')
    return s


HEAD_PART = r'(new ModelPart\(Collections\.emptyList\(\), Collections\.emptyMap\(\)\)|new Model\w+\(Minecraft\.getInstance\(\)\.getEntityModels\(\)\.bakeLayer\(Model\w+\.LAYER_LOCATION\)\)\.\w+)'


def armor_models(s):
    """MCreator armor with Blockbench models: NeoForge 21.11's getHumanoidArmorModel(stack, layer type, original);
    the render state poses the model (crouching, riding, young), so the model is built once; the texture override
    moved to the client extensions"""
    if 'public HumanoidModel getHumanoidArmorModel(' not in s:
        return s
    s = re.sub(r'public HumanoidModel getHumanoidArmorModel\(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel\) \{\n(\t+)HumanoidModel armorModel = (new HumanoidModel\(.*?\));\n'
               r'\t+armorModel\.crouching = living\.isShiftKeyDown\(\);\n\t+armorModel\.riding = defaultModel\.riding;\n\t+armorModel\.young = living\.isBaby\(\);\n\t+return armorModel;',
               lambda m: 'public net.minecraft.client.model.Model getHumanoidArmorModel(ItemStack stack, net.minecraft.client.resources.model.EquipmentClientInfo.LayerType layerType, net.minecraft.client.model.Model original) {\n'
                         + m.group(1) + 'if (this.armorModel == null)\n' + m.group(1) + '\tthis.armorModel = ' + m.group(2) + ';\n' + m.group(1) + 'return this.armorModel;', s, flags=re.S)
    s = re.sub(r'"head", ' + HEAD_PART, rf'"head", {PKG}.port.PortArmorModels.head(\1)', s)
    s = re.sub(r'(\n(\t+)@Override\n(?:\t+@net\.neoforged[^\n]*\n)?\t+public net\.minecraft\.client\.model\.Model getHumanoidArmorModel)', r'\n\2private HumanoidModel<?> armorModel;\n\1', s)
    # the texture of the piece (getArmorTexture of NeoForge's item extension in 1.21.1)
    s = re.sub(r'(\t\t\t\};\n\t\t\}\n)\n\t\tpublic Identifier getArmorTexture\(ItemStack stack, Entity entity, EquipmentSlot slot, Object layer, boolean innerModel\) \{\n\t\t\treturn (Identifier\.parse\("[^"]+"\));\n\t\t\}\n',
               r'\n\t\t\t\t@Override\n\t\t\t\tpublic Identifier getArmorTexture(ItemStack stack, net.minecraft.client.resources.model.EquipmentClientInfo.LayerType type, net.minecraft.client.resources.model.EquipmentClientInfo.Layer layer, Identifier _default) {\n\t\t\t\t\treturn \2;\n\t\t\t\t}\n\1', s)
    return s


JEI_RECIPE_METHODS = """
	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public net.minecraft.world.item.crafting.PlacementInfo placementInfo() {
		return net.minecraft.world.item.crafting.PlacementInfo.NOT_PLACEABLE;
	}

	@Override
	public net.minecraft.world.item.crafting.RecipeBookCategory recipeBookCategory() {
		return net.minecraft.world.item.crafting.RecipeBookCategories.CRAFTING_MISC;
	}
"""


def jei(s):
    """MCreator's JEI recipe types: 1.21.2 recipes (no ingredient list or result in the interface, placement info,
    recipe book category, no empty ingredient), JEI 27 (IRecipeType, categories draw their background), recipes
    synced to the client (port.PortRecipeSync)"""
    m = re.search(r'public class (\w+) implements Recipe<RecipeInput> \{', s)
    if m:
        cls = m.group(1)
        s = s.replace('\t@Override\n\tpublic NonNullList<Ingredient> getIngredients()', '\tpublic NonNullList<Ingredient> getIngredients()')
        s = s.replace('\t@Override\n\tpublic ItemStack getResultItem(', '\tpublic ItemStack getResultItem(')
        s = re.sub(r'\t@Override\n\tpublic boolean canCraftInDimensions\(int pWidth, int pHeight\) \{\n\t\treturn true;\n\t\}\n\n', '', s)
        s = s.replace('public RecipeType<?> getType() {', f'public RecipeType<{cls}> getType() {{')
        s = s.replace('public RecipeSerializer<?> getSerializer() {', f'public RecipeSerializer<{cls}> getSerializer() {{')
        if 'placementInfo()' not in s:
            s = s.replace('\tpublic static class Type implements', JEI_RECIPE_METHODS.lstrip('\n') + '\n\tpublic static class Type implements', 1)
        s = s.replace('Ingredient.CODEC_NONEMPTY', 'Ingredient.CODEC')
        s = s.replace('NonNullList.of(Ingredient.EMPTY, aingredient)', 'NonNullList.of(aingredient[0], aingredient)')
        s = s.replace('NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);\n\t\t\tinputs.replaceAll(ingredients -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));',
                      'int size = buf.readVarInt();\n\t\t\tNonNullList<Ingredient> inputs = NonNullList.createWithCapacity(size);\n\t\t\tfor (int i = 0; i < size; i++)\n\t\t\t\tinputs.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));')
    if 'implements IRecipeCategory<' in s:
        s = s.replace('public mezz.jei.api.recipe.RecipeType<', 'public mezz.jei.api.recipe.types.IRecipeType<')
        s = re.sub(r'\t@Override\n\tpublic IDrawable getBackground\(\) \{\n\t\treturn this\.background;\n\t\}\n',
                   '\t@Override\n\tpublic int getWidth() {\n\t\treturn this.background.getWidth();\n\t}\n\n\t@Override\n\tpublic int getHeight() {\n\t\treturn this.background.getHeight();\n\t}\n\n'
                   '\t@Override\n\tpublic void draw(%s recipe, mezz.jei.api.gui.ingredient.IRecipeSlotsView recipeSlotsView, net.minecraft.client.gui.GuiGraphics guiGraphics, double mouseX, double mouseY) {\n\t\tthis.background.draw(guiGraphics);\n\t}\n'
                   % re.search(r'implements IRecipeCategory<(\w+)>', s).group(1), s)
    if 'implements IModPlugin' in s:
        s = re.sub(r'mezz\.jei\.api\.recipe\.RecipeType<(\w+)> (\w+) = new mezz\.jei\.api\.recipe\.RecipeType<>\(', r'mezz.jei.api.recipe.types.IRecipeType<\1> \2 = mezz.jei.api.recipe.types.IRecipeType.create(', s)
        s = re.sub(r'\n\t\tRecipeManager recipeManager = [^\n]*;', '', s)
        s = s.replace('recipeManager.getAllRecipesFor(', f'{PKG}.port.PortRecipeSync.recipes(')
    return s


REPAIR_DIR = 'common/src/main/resources/data/the_deep_void/tags/item/repair/'


def repairable(s, name):
    """1.21.2: an item's repair materials are a component (Properties.repairable); the materials of
    isValidRepairItem go to an item tag the_deep_void:repair/<item>"""
    m = re.search(r'\t@Override\n\tpublic boolean isValidRepairItem\(ItemStack itemstack, ItemStack repairitem\) \{\n\t\treturn Ingredient\s*\.of\((.*?)\)\s*\.test\(repairitem\);\n\t\}\n\n', s, re.S)
    if not m:
        return s
    ids = ['the_deep_void:' + c.lower() for c in re.findall(r'TheDeepVoidMod(?:Items|Blocks)\.(\w+)\.get\(\)', m.group(1))]
    ids += ['minecraft:' + c.lower() for c in re.findall(r'\b(?:Items|Blocks)\.(\w+)\b', m.group(1))]
    tag = re.sub(r'(?<!^)(?=[A-Z])', '_', name.removesuffix('Item')).lower()
    os.makedirs(REPAIR_DIR, exist_ok=True)
    with open(REPAIR_DIR + tag + '.json', 'w') as f:
        json.dump({'replace': False, 'values': ids}, f, indent=2)
        f.write('\n')
    s = s[:m.start()] + s[m.end():]
    return s.replace(f'{REGISTRAR}.itemProps()', f'{REGISTRAR}.itemProps().repairable(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "repair/{tag}")))', 1)


def items4(s):
    """1.21.2-1.21.5 entity and item method changes MCreator overrides"""
    s = s.replace('public boolean canBeCollidedWith() {', 'public boolean canBeCollidedWith(@javax.annotation.Nullable net.minecraft.world.entity.Entity other) {')
    s = s.replace('super.canBeCollidedWith()', 'super.canBeCollidedWith(other)')
    s = s.replace('public boolean causeFallDamage(float l, float d, DamageSource source) {', 'public boolean causeFallDamage(double l, float d, DamageSource source) {')
    s = s.replace('public boolean canChangeDimensions(Level from, Level to) {', 'public boolean canTeleport(Level from, Level to) {')
    s = s.replace('public void customServerAiStep() {\n\t\tsuper.customServerAiStep();', 'public void customServerAiStep(ServerLevel level) {\n\t\tsuper.customServerAiStep(level);')
    if 'customServerAiStep(ServerLevel' in s:
        s = add_import(s, 'net.minecraft.server.level.ServerLevel')
    # hurtEnemy returns nothing since 1.21.2
    pos = 0
    while (m := re.search(r'public boolean hurtEnemy\(ItemStack (\w+), LivingEntity (\w+), LivingEntity (\w+)\) \{', s[pos:])):
        start = pos + m.start()
        open_brace = pos + m.end() - 1
        close = method_body(s, open_brace)
        body = re.sub(r'\n\t\treturn (?:true|false|retval);\n\t$', '\n\t', s[open_brace:close])
        body = re.sub(r'\breturn (?:true|false|retval);', 'return;', body)
        body = body.replace('boolean retval = super.hurtEnemy(', 'super.hurtEnemy(')
        s = s[:start] + s[start:open_brace].replace('public boolean hurtEnemy', 'public void hurtEnemy') + body + s[close:]
        pos = start + 10
    # 1.21.2: the enchantability is an item property
    m = re.search(r'\t@Override\n\tpublic int getEnchantmentValue\(\) \{\n\t\treturn (\d+);\n\t\}\n\n', s)
    if m and f'{REGISTRAR}.itemProps()' in s:
        s = s[:m.start()] + s[m.end():]
        s = s.replace(f'{REGISTRAR}.itemProps()', f'{REGISTRAR}.itemProps().enchantable({m.group(1)})', 1)
    # 1.21.2: the crafting remainder is stack-based on both loaders (NeoForge getCraftingRemainder, Fabric
    # FabricItem.getRecipeRemainder); the overrides match by name, as the common code sees neither interface
    m = re.search(r'\t@Override\n\tpublic boolean hasCraftingRemainingItem\(\) \{\n\t\treturn true;\n\t\}\n\n\tpublic ItemStack getCraftingRemainingItem\(ItemStack itemstack\) \{\n(.*?)\n\t\}\n', s, re.S)
    if m:
        s = s[:m.start()] + ('\t/** NeoForge IItemExtension */\n\tpublic ItemStack getCraftingRemainder(ItemStack itemstack) {\n' + m.group(1) + '\n\t}\n\n'
                             '\t/** Fabric FabricItem */\n\tpublic ItemStack getRecipeRemainder(ItemStack itemstack) {\n\t\treturn getCraftingRemainder(itemstack);\n\t}\n') + s[m.end():]
    # 1.21.2: an enchantability of 0 (no enchanting table, as in 1.21.1) is not allowed: 1, then the component goes
    m = re.search(r'(new ToolMaterial\([^;]*?, )0(, net\.minecraft\.tags\.TagKey\.create)', s)
    if m and re.search(r'\.(sword|pickaxe)\(TOOL_TIER, [^)]*\)\)', s):
        s = s[:m.start()] + m.group(1) + '1' + m.group(2) + s[m.end():]
        s = re.sub(r'(\.(?:sword|pickaxe)\(TOOL_TIER, [^)]*\))\)', r'\1.component(net.minecraft.core.component.DataComponents.ENCHANTABLE, null))', s, count=1)
    # 1.21.2: fluids convert to sources on the server; 1.21.11 comparator output knows the side
    s = s.replace('protected boolean canConvertToSource(Level level) {', 'protected boolean canConvertToSource(ServerLevel level) {')
    if 'canConvertToSource(ServerLevel' in s:
        s = add_import(s, 'net.minecraft.server.level.ServerLevel')
    s = s.replace('public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {', 'public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos, net.minecraft.core.Direction direction) {')
    m = re.search(r'public class (\w+Item) extends', s)
    if m:
        s = repairable(s, m.group(1))
    return s


def item_properties(s):
    """1.21.4: item models select by item model definitions; MCreator's property procedures go to
    port.PortItemProperties (the definitions use the_deep_void:procedure), shields use minecraft:using_item"""
    if not re.search(r'(?<![\w.])ItemProperties\.register\(', s):
        return s
    s = re.sub(r'\n\t*ItemProperties\.register\(\w+\.get\(\), Identifier\.parse\("blocking"\), [^\n]*;', '', s)
    s = re.sub(r'(?<![\w.])ItemProperties\.register\(', f'{PKG}.port.PortItemProperties.register(', s)
    s = s.replace('import net.minecraft.client.renderer.item.ItemProperties;\n', '')
    return s


def screens(s):
    """MCreator container screens: 1.21.6 GUI batching (no GL state calls, blits name their pipeline), 1.21.9 key
    events, 1.21.11 buttons draw their contents (renderWidget is final)"""
    if 'extends AbstractContainerScreen<' not in s:
        return s
    s = re.sub(r'\n\t*RenderSystem\.[^\n]*;', '', s)
    s = s.replace('import com.mojang.blaze3d.systems.RenderSystem;\n', '')
    s = re.sub(r'guiGraphics\.blit\((?!RenderPipelines)', 'guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ', s)
    s = s.replace('public boolean keyPressed(int key, int b, int c) {', 'public boolean keyPressed(net.minecraft.client.input.KeyEvent event) {\n\t\tint key = event.key();')
    s = s.replace('return super.keyPressed(key, b, c);', 'return super.keyPressed(event);')
    s = re.sub(r'public void renderWidget\(GuiGraphics guiGraphics, int gx, int gy, float ticks\) \{(\n[^\n]*\n\t+)super\.renderWidget\(guiGraphics, gx, gy, ticks\);',
               r'public void renderContents(GuiGraphics guiGraphics, int gx, int gy, float ticks) {\1super.renderContents(guiGraphics, gx, gy, ticks);', s)
    if 'RenderPipelines.' in s:
        s = add_import(s, 'net.minecraft.client.renderer.RenderPipelines')
    return s


def fog(s):
    """1.21.6: no fog modes and shapes (port.bus.ViewportEvent keeps MCreator's names)"""
    if 'import net.sombrius.thedeepvoid.port.bus.ViewportEvent;' not in s:
        return s
    s = s.replace('FogRenderer.FogMode.', 'ViewportEvent.FogMode.')
    s = s.replace('import com.mojang.blaze3d.shaders.FogShape;\n', '')
    s = re.sub(r'(?<![\w.])FogShape\b', 'ViewportEvent.FogShape', s)
    if 'FogRenderer' not in s.replace('import net.minecraft.client.renderer.fog.FogRenderer;', ''):
        s = s.replace('import net.minecraft.client.renderer.fog.FogRenderer;\n', '')
    return s


def dimensions(s):
    """1.21.11: dimension effects are dimension type data (data/the_deep_void/dimension_type)"""
    s = re.sub(r'\t@EventBusSubscriber\(value = Dist\.CLIENT\)\n\tpublic static class \w+SpecialEffectsHandler \{\n.*?\n\t\}\n\n?', '', s, flags=re.S)
    s = s.replace('import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;\n', '')
    for imp in ('net.minecraft.client.renderer.DimensionSpecialEffects', 'net.minecraft.world.phys.Vec3', 'net.minecraft.resources.Identifier',
                f'{PKG}.port.bus.RegisterDimensionSpecialEffectsEvent', f'{PKG}.port.bus.Dist'):
        rest = s.replace('import ' + imp + ';\n', '')
        if not re.search(r'\b' + imp.rsplit('.', 1)[1] + r'\b', rest):
            s = rest
    return s


def init(s):
    # 1.21.2: a potion has a name (its registry name)
    s = re.sub(r'REGISTRY\.register\("(\w+)",(\s*)\(\) -> new Potion\((?!")', r'REGISTRY.register("\1",\2() -> new Potion("\1", ', s)
    # 1.21.9: key mapping categories are registered ids (key.category.the_deep_void.deep_void)
    s = s.replace('"key.categories.deep_void"', f'{PKG}.port.PortClient.KEY_CATEGORY')
    # 1.21.11: game rules are registry entries
    s = re.sub(r'getGameRules\(\)\.getBoolean\(TheDeepVoidModGameRules\.(\w+)\)', r'getGameRules().get(TheDeepVoidModGameRules.\1.get())', s)
    return s


def port(s):
    s = s.replace('.getGameProfile().getId()', '.getGameProfile().id()')
    s = tiers(s)
    s = properties(s)
    s = items2(s)
    s = tools(s)
    s = use(s)
    s = armor(s)
    s = items3(s)
    s = blocks(s)
    s = saves(s)
    s = particles(s)
    s = init(s)
    s = procs(s)
    s = fog(s)
    s = screens(s)
    s = item_properties(s)
    s = jei(s)
    s = items4(s)
    s = armor_models(s)
    if 'SpecialEffectsHandler' in s:
        s = dimensions(s)
    return s


# the NeoForge-only MCreator files of tools/manual-files.txt ("neoforge: ..."), restored into the neoforge module
NEOFORGE_ONLY = ['neoforge/src/main/java/' + line.split(':', 1)[1].strip() for line in open('tools/manual-files.txt') if line.startswith('neoforge:')]


def main():
    changed = 0
    for path in glob.glob(ROOT + '**/*.java', recursive=True) + NEOFORGE_ONLY:
        s = open(path, encoding='utf-8').read()
        o = port(s)
        if o != s:
            open(path, 'w', encoding='utf-8', newline='\n').write(o)
            changed += 1
    os.makedirs(TAGS, exist_ok=True)
    for name, items in sorted(REPAIR_TAGS.items()):
        with open(TAGS + name + '.json', 'w') as f:
            json.dump({'values': items}, f, indent=2)
            f.write('\n')
    # only the first pass (on the MCreator originals) sees the armor materials
    if ARMOR_ASSETS:
        with open('tools/armor_assets.json', 'w') as f:
            json.dump(dict(sorted(ARMOR_ASSETS.items())), f, indent=2)
            f.write('\n')
    print('changed', changed, '; repair tags', len(REPAIR_TAGS), '; armor materials', len(ARMOR_ASSETS))


if __name__ == '__main__':
    main()
