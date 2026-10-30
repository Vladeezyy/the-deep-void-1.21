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
    attrs = r'(?:net\.sombrius\.thedeepvoid\.port\.PortUtil\.swordAttributes|SwordItem\.createAttributes|DiggerItem\.createAttributes)\(TOOL_TIER, ([-\d.]+f?), ([-\d.]+f?)\)'
    if kind in TOOLS:
        s = re.sub(r'super\(TOOL_TIER, (new Item\.Properties\(\)|' + re.escape(REGISTRAR) + r'\.itemProps\(\))\.attributes\(' + attrs + r'\)',
                   lambda x: f'super({REGISTRAR}.itemProps().{TOOLS[kind]}(TOOL_TIER, {x.group(2)}, {x.group(3)})', s)
        s = re.sub(r'(public (?:abstract )?class \w+ extends )' + kind + r'\b', r'\1Item', s)
    else:
        s = re.sub(r'super\(TOOL_TIER, (new Item\.Properties\(\)|' + re.escape(REGISTRAR) + r'\.itemProps\(\))\.attributes\(' + attrs + r'\)',
                   lambda x: f'super(TOOL_TIER, {x.group(2)}, {x.group(3)}, {REGISTRAR}.itemProps()', s)
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


def port(s):
    s = s.replace('.getGameProfile().getId()', '.getGameProfile().id()')
    s = tiers(s)
    s = properties(s)
    s = items2(s)
    s = tools(s)
    s = use(s)
    s = armor(s)
    return s


def main():
    changed = 0
    for path in glob.glob(ROOT + '**/*.java', recursive=True):
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
    with open('tools/armor_assets.json', 'w') as f:
        json.dump(dict(sorted(ARMOR_ASSETS.items())), f, indent=2)
        f.write('\n')
    print('changed', changed, '; repair tags', len(REPAIR_TAGS), '; armor materials', len(ARMOR_ASSETS))


if __name__ == '__main__':
    main()
