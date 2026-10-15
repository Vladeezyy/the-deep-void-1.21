# The Deep Void — port log

Port of **The Deep Void v1.99.3.1** (Sombrius / TroupeMaster, MIT, made with MCreator) to NeoForge.
Targets: Minecraft 1.21.1 first, then every 26.x release (26.1.x, 26.2, 26.3).

## Sources

| What | Where |
| --- | --- |
| Forge 1.20.1, v1.99.3.1 (the content spec) | github.com/TroupeMaster/TheDeepVoid `f4d0bb06` (package `net.sombrius.thedeepvoid`) |
| NeoForge 1.20.6, v1.98.1 (the author's own Forge → NeoForge translation) | github.com/TroupeMaster/The-Deep-Void-NeoForge `af5f1e2` (package `net.mcreator.thedeepvoid`) |
| Forge 1.20.1, v1.98.1 (merge base) | TheDeepVoid `e21af988` |

## How the code was made

1. **Three-way merge** (`tools/merge_upstreams.py`): for every 1.99.3.1 file, base = Forge 1.98.1, ours = NeoForge 1.98.1,
   theirs = Forge 1.99.3.1 (imports normalised first, MCreator reorders them). Result: 2468 files taken from the NeoForge
   build unchanged, 184 merged cleanly, 159 conflicts and 305 files new in 1.99 taken from Forge 1.99.3.1
   (`tools/port-conflicts.txt`, `tools/port-forge-only.txt`).
2. **Mechanical conversion** (`tools/port_java.py`, idempotent): Forge → NeoForge APIs and NeoForge 1.20.6 → 1.21.1 APIs.
   Rules are taken from the 1697 file pairs that differ between Forge 1.98.1 and NeoForge 1.98.1, plus the 1.21 changes.
   `tools/port_armor.py` (armor materials), `tools/port_messages.py` (GUI packets).
3. Hand fixes for the rest (menus, recipes for JEI, registries, client extensions).

## Resources

`tools/convert_resources.py <upstream resources> src/main/resources` rebuilds `assets/` and `data/` from the 1.99.3.1
resources: 1.21 folder names (`loot_table`, `recipe`, `advancement`, `tags/item` …), `forge` tags → `c`,
`forge/biome_modifier` → `neoforge/biome_modifier`, recipe results (`item` → `id`), item predicates (`items`,
`predicates`), `enchant_with_levels` treasure → `options`, int/float providers without the `value` wrapper, trim material
overrides with namespaces. Hand-written 1.21 data lives in `tools/overlay/` and is copied on top.

## Deviations from 1.20.1 (why)

- **Enchantments are data** (1.21): the 14 enchantment classes became `data/the_deep_void/enchantment/*.json`.
  Supported items = the 1.20.1 category ∪ the `canApplyAtEnchantingTable` items (1.21 requires primary ⊆ supported);
  the enchanting table uses the latter (`primary_items`). Costs are the 1.20.1 defaults (min 1 + 10·level, max min + 5).
  Treasure / curse / tradeable / discoverable flags became vanilla enchantment tags; `checkCompatibility` lists became
  `exclusive_set` tags. Void Vision was neither discoverable nor allowed on books: it is in no tag (never offered by the
  table, loot or villagers), as in 1.20.1.
- **Mob types** (removed in 1.20.5): undead / arthropod mobs are in the `minecraft:undead` / `minecraft:arthropod` entity tags
  (`tools/mobtypes.json`, read from the 1.99 sources), which is what Smite / Bane of Arthropods / Healing use in 1.21.
- **Music discs** are 1.21 jukebox songs (`data/the_deep_void/jukebox_song`), same sound, length and comparator output.
- **Paintings** are 1.21 painting variants (data), sizes from the 1.20.1 registry.
- **Armor materials** are registered (1.20.5); durability moved to the item properties with the same numbers.
- **Arrow knockback**: 1.21 only takes knockback from the bow's enchantments; `PortUtil.setKnockback` + `AbstractArrowMixin`
  keep the per-projectile knockback of 1.20.1 with the 1.20.1 formula.
- **Item damage without a level** (`hurtAndBreak(…, RandomSource, …)`): `PortUtil.hurtAndBreak` uses the entity's level or
  the overworld; Unbreaking applies as in 1.20.1.
- **Effect start / end hooks**: 1.20.1 `addAttributeModifiers` → `onEffectStarted`; `removeAttributeModifiers` → called from
  `MobEffectEvent.Remove/Expired` (`PortEvents`), as MCreator does on NeoForge. 1.20.1 also ran the "expires" procedure when
  an effect was re-applied with a higher level; 1.21 does not.
- **Attribute modifiers** are identified by `the_deep_void:<the 1.20.1 UUID>`.
- **Item NBT** reads and writes use the `minecraft:custom_data` component (same keys).
- **Arm poses** of the guns: NeoForge 21.1 extends `HumanoidModel.ArmPose` from `META-INF/enumextensions.json` (`port/ArmPoses`).
- **Client extensions** (item renderers, effect icons, fluid textures) are registered by `RegisterClientExtensionsEvent`
  (`port/ClientExtensionsRegistration`), the 1.21 replacement of `initializeClient`.
- **Image buttons** of the 1.99 GUIs keep the 1.20 texture layout (`port/PortImageButton`), so the original textures work.
- **Food**: `meat()` is the `minecraft:meat` item tag in 1.21 (cooked flesh, disgusting meal, opened dead man's hand).
- Data that referenced an entity removed in 1.99 is skipped (the Mimicking Shadow biome modifier: the entity no longer exists).
- **Lakes of Liquid Void / Liquid Ichor** are placed 8 blocks back on x and z (centred on the spot the 1.20.1 placement
  picked). 1.20.5 lets a feature reach only the neighbouring chunks and the freeze check of water-tagged lakes reads further;
  unchanged, world generation crashed ("Requested chunk unavailable").
- Data left in the 1.99 sources for removed content is skipped: the Desolate Soil feature (code removed in 1.96.8) and the
  Mimicking Shadow spawns (entity removed in 1.99); 1.20.1 would not load a world with them either.

## Localization

- English text that 1.99.3.1 wrote straight into the code (armor and item tooltips, chat messages, JEI category names;
  40 classes) is `Component.translatable("text.the_deep_void.port.*")` now (`tools/localize_literals.py`), with the same
  English text in `en_us`.
- Translations live in `tools/lang/<language>.json` and `convert_resources.py` merges them over the upstream lang files:
  Russian and Simplified Chinese completed, Ukrainian (`uk_ua`), German (`de_de`), Spanish (`es_es`) and Brazilian
  Portuguese (`pt_br`) added. Every required language (en, ru, uk, de, es, pt_br) has all keys of `en_us`; the untranslatable
  ones carry the English text. Names that stay the
  same in every language (music disc titles, `Combo`, …) are listed in `tools/lang/untranslatable.txt`;
  `tools/lang_todo.py <language>` lists what is still untranslated.

## Known, unchanged from 1.20.1

- Rotten bone pile deltas (vanilla `delta_feature`, size up to 16 + rim up to 8) can reach past the chunks a feature may
  write to; the game drops those few blocks and logs "Detected setBlock in a far chunk". 1.20.1 did exactly the same.
