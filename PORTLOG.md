# The Deep Void — port log

Port of **The Deep Void v1.99.3.1** (Sombrius / TroupeMaster, MIT, made with MCreator) to NeoForge and Fabric.
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

## Performance: tick handlers (2026-10-30)

MCreator's per-tick procedures, measured with the bench scene (`tools/bench.sh`, `DevScene` + `PortBench`):
a survival player with 100 pigs without AI around, in the overworld, in the Forgotten Valley, and there with a stalker,
a watching stalker, an overseer and a void dweller behind the player. Every place gets 200-tick windows alternating
the **base line** (the same world with the mod's tick subscribers taken off the event bus) and the mod. The times are
those of all the listeners of the event (first to last listener), per tick. Mean of 4 windows per cell; the before
and after runs were alternated twice (`tools/bench_table.py`). Apple M4, JDK 21, dev client with the integrated server.

| Place | Measure | Base line | Before | After |
| --- | --- | ---: | ---: | ---: |
| Overworld | player tick, server, µs | 10 | 126 | 99 |
| Overworld | player tick, client, µs | 7 | 88 | 74 |
| Overworld | entity ticks (105 entities), server, µs | 10.9 | 36.1 | 8.7 |
| Overworld | level ticks, µs | 3.2 | 11.6 | 5.3 |
| Overworld | sync packets / s | 0 | 100 | 0 |
| Deep Void | player tick, server, µs | 14 | 232 | 199 |
| Deep Void | player tick, client, µs | 8 | 138 | 127 |
| Deep Void | entity ticks, server, µs | 11.7 | 58.4 | 12.6 |
| Deep Void | level ticks, µs | 3.8 | 28.3 | 6.5 |
| Deep Void | sync packets / s | 0 | 160 | 20 |
| Deep Void + stalkers, overseer | player tick, server, µs | 3 | 198 | 123 |
| Deep Void + stalkers, overseer | player tick, client, µs | 2 | 127 | 77 |
| Deep Void + stalkers, overseer | entity ticks, server, µs | 10.2 | 38.9 | 16.8 |
| Deep Void + stalkers, overseer | level ticks, µs | 2.5 | 19.6 | 4.8 |
| Deep Void + stalkers, overseer | sync packets / s | 0 | 140 | 20 |
| all three | MSPT, ms | 1.5–2.2 | 1.8–2.1 | 1.7–2.2 |
| all three | FPS | 302 / 611 / 480 | 329 / 595 / 464 | 294 / 592 / 461 |

- MSPT and FPS of one player in a dev world are within the noise (±0.5 ms, ±15 %); the handler times are what moved.
  The handler numbers are per player: a server with 10 players pays them 10 times, and before the fix every
  map-variable sync went to **every** player, every tick.
- The windows run 1–3 minutes after start, before the JIT has compiled handlers called 20 times a second; long play
  should be cheaper in both columns.
- **Fixes** (`c1b7802`, mechanical part `tools/port_perf.py`, idempotent):
  - `getEntitiesOfClass(Stalker/Overseer/…, box up to 250 blocks)` every tick, plus 20–40 small searches along the
    view ray → `PortEntities.find`: per-level list of the 10 entity classes those handlers look for, kept from
    `onTrackingStart` to `onTrackingEnd` (mixins on `ServerLevel/ClientLevel$EntityCallbacks`), the same set
    `getEntitiesOfClass` sees. The ray loops that only acted on one class ask for that class. The bench checks the list
    against `getEntitiesOfClass` on both sides in every place (`[bench] index … ok`).
  - Player variables (~80 fields + 4 item stacks as NBT) were synced after every change, up to 8 times a tick →
    `PortSync`: one packet at the end of the player's tick.
  - `hunterSpawnCooldown` and `undertakerSpawn` (read only on the server) were sent to all players every tick per
    level / per player → saved (`setDirty`), not sent.
  - `ResourceKey` / `TagKey` / `ResourceLocation` built from strings on every call → constants.
  - `PortUtil.enchantmentLevel` (armor checks, up to 9 a tick): no registry lookup and no `GetEnchantmentLevelEvent`
    for an empty slot, holders cached per registry.
  - `EntityAnimationFactory` ran ~110 `instanceof` on every entity of the world every tick → only the mod's classes.
- Left as in 1.20.1: what the remaining ~25 handlers cost (each a few µs of attribute, item and variable checks).

## NeoForge + Fabric (1.21.1, 2026-10-30)

`common` (the MCreator code, compiled against vanilla through NeoForm), `neoforge`, `fabric`; the build logic is in
`buildSrc` (MultiLoader layout). The split is repeatable from the NeoForge-only sources:
`tools/restore_common.sh` (the originals of `main`, minus the hand-ported files of `tools/manual-files.txt`) and then
`tools/port_multiloader.py` (idempotent).

- **Events**: NeoForge's event classes with NeoForge's names and methods in `port/bus`; the script points the imports
  there and generates `EventIndex` / `ClientEventIndex` from the `@SubscribeEvent` methods (a method or nested class with
  `Dist.CLIENT` is a client listener). NeoForge forwards its events (`TheDeepVoidNeoForge`, `...Client`); Fabric posts
  Fabric API events and mixins at the place NeoForge fires each event (`fabric/mixin`: damage after the invulnerability
  checks, death in `die`, fall, heal, effect removal / expiry, item use start / stop / finish, experience drop, mob
  target, dimension travel, block placement, XP change, entity tick, brewing).
- **NeoForge API** the code uses outside events, with NeoForge's names in `port/neo`: item handlers (`IItemHandler`,
  `ItemStackHandler`, `SlotItemHandler`, `InvWrapper`, `SidedInvWrapper`, `ComponentItemHandler`; `PortCaps` answers
  `getCapability(Capabilities.ItemHandler.*)`, NeoForge also registers them as real capabilities), `ModConfigSpec`
  (the same `config/Deep Void Config.toml` on both loaders), `IBrewingRecipe`, `DeferredSoundType`, `BaseFlowingFluid`,
  payloads (`IPayloadContext`, `PacketDistributor` → `Platform`), client item / effect extensions.
- **NeoForge-only overrides** (no `@Override` in common, NeoForge calls them): `onDestroyedByPlayer` and `onEntitySwing`
  (Fabric: mixins through `PortBlockHooks` / `PortItemHooks`), sky / clouds / weather of the dimension effects (Fabric:
  `LevelRenderer` mixin), `getFlammability` (Fabric: `FlammableBlockRegistry`), `canSustainPlant` (Fabric: the soils are
  in `minecraft:dirt`), `getEnchantPowerBonus` (Fabric: the ancient bookshelf is an enchantment power provider, worth 1
  as every bookshelf instead of 3). Without a Fabric counterpart: `canConnectRedstone`, `getBeaconColorMultiplier`,
  `getBlockPathType`, `canDrownInFluidType`, the stack-aware `getCraftingRemainingItem`.
- `@OnlyIn(Dist.CLIENT)` stays NeoForge's annotation and gets Fabric's `@Environment(EnvType.CLIENT)` next to it: both
  loaders strip those members on a dedicated server (MCreator code in them uses client classes). NeoForge types of
  NeoForge-only overrides (`TriState`, `FluidType`) are stubs in `common/src/stubs`; the Fabric jar ships them (the JVM
  resolves them when it verifies those methods).
- **Fabric data**: player variables and NeoForge's persistent data are data attachments (same ids); the NeoForge biome
  modifiers are read from the jar (`FabricBiomeModifiers`); NeoForge's `neoforge:poison` damage type and its tags ship
  with the Fabric jar.
- Fabric arm poses: no enum extension, so the guns use vanilla's ITEM pose and their transformer runs after
  `HumanoidModel.setupAnim`; GeckoLib wings go through GeckoLib's armor path (`prepForRender` with the buffer source).
- Tick handler cost after the split (bench scene, µs per player tick, server / client): NeoForge 101–107 / 56–57 in the
  overworld, 188–200 / 83–130 in the Deep Void, 119–161 / 61–77 with the stalkers; Fabric 55–60 / 76–79, 137 / 102,
  45–73 / 54–72 (the Fabric mixins post straight on the mod's bus). Sync packets 0–20 per second on both.
- Checked: dev server (both), production server (NeoForge 21.1.259 with the jar, Fabric `prodServer`), the dev scene
  (biomes, mobs) and the items scene (guns, armor models, wings) with matching screenshots on both loaders.

## Minecraft 1.21.11 (branch mc/1.21.11, 2026-10-30)

NeoForge 21.11.48, Fabric API 0.141.6, GeckoLib 5.4.5, JEI 27.54. Repeatable from the 1.21.1 sources:
`tools/port_1_21_11.sh` (restore the MCreator originals, multiloader split, the generic 1.21.11 rules of the Midnight
Lurker port, the Deep Void rules `tools/port_dv_1_21_11.py`, GeckoLib 5, render states, moved imports), then
`tools/port_resources_1_21_11.py` for assets and data. Both scripts are idempotent.

- **Items**: tool and armor materials are records (repair items as tags `the_deep_void:tool_materials/*`, armor
  layers as equipment assets from `tools/armor_assets.json`, textures in `textures/entity/equipment`); every item has an
  item model definition. MCreator's item properties (bows, states) are one range-select property
  `the_deep_void:procedure` (`port.PortItemProperties`), shields use `minecraft:using_item`; spawn eggs are two layers
  of an own template tinted with the 1.20.1 colors. Tooltips of blocks come through `port.PortBlockItem`. Crafting
  remainders are stack-based on both loaders. Enchantability 0 (three blades) means "no enchanting table" as before.
- **GeckoLib 5**: entities and renderers on render states; the seven guns share `port.GeoArmsItemRenderer` (the
  player's arms on the RightArm / LeftArm bones in first person, from an own baked player model) and come from the
  items' render providers (`geckolib:geckolib` special item model); the wings are GeckoLib armor (GeckoLib draws them
  on both loaders, no Fabric code any more).
- **Armor models** (hunter, vulture, plague doctor): built once per item instead of every frame; the render state poses
  them. Fabric: `ArmorRenderer` with `submitTransformCopyingModel`.
- **Sky and fog**: no `DimensionSpecialEffects`. The Deep Void and The Pit are data: `skybox: overworld` without sun,
  moon and stars (`mixins.SkyRendererMixin`), no clouds, a fixed midnight baked into the biome colors (1.21.1 darkened
  them by the time of day). MCreator's fog procedures set the terrain fog of the frame (`FogData`; the sky and clouds
  fade with it); fog shapes are gone (always spherical).
- **Renderers** with layers shown by procedures compute the conditions with the render state (`PortRender.LayerState`).
- **JEI**: the three display-only recipe types are synced to the client (NeoForge `OnDatapackSyncEvent.sendRecipes`,
  Fabric `RecipeSynchronization`) into `port.PortRecipeSync`; brewing recipes have ids.
- **Data**: biome visuals and sounds are environment attributes, carvers a list, the noise router has a preliminary
  surface level, recipes use id ingredients, trim patterns / materials as 1.21.5 (the materials come from the items'
  `provides_trim_material`, `port.PortTrimMaterials`), loot tables lost the air entries.
- **NeoForge 21.11** strips no `@OnlyIn` any more: all are gone (a load warning screen otherwise); client-only code that
  a dedicated server would verify (the tooltips' "client player" fallback) goes through `PortUtil.clientPlayer`. Item
  capabilities use the transfer API (vanilla container wrappers); player variables are saved under `data` in the
  attachment (1.21.1 worlds start with fresh player variables on NeoForge).
- **Fabric**: attribute modifiers of the items are added when the game applies them (tags and config are not ready at
  init); gun arm poses go through the humanoid render state (named after the item); block render layers come from a
  map the resource script builds from the models (Fabric reads no `render_type`).
- Checked: dev server (both), production server (Fabric `prodServer` with the jar), the dev scene (biomes, mobs) and the
  items scene (guns in third person, Blockbench armor, GeckoLib wings) on both loaders. Not checked yet: a NeoForge
  production server with the jar, the guns' first-person arms (the scene shows none on 1.21.1 either), MSPT / FPS.

## Minecraft 26.1.2 (branch mc/26.1.2, 2026-10-31)

NeoForge 26.1.2.125, Fabric API 0.155.3, GeckoLib 5.5.2 (Modrinth only, `com.geckolib`), JEI 29.53; the game is not
obfuscated: Java 25, Fabric Loom 1.18 (`net.fabricmc.fabric-loom`, access widener in the `official` namespace, the jar
task instead of remapJar). Repeatable from the originals: `tools/port_26_1.sh` (the 1.21.11 pipeline, then
`tools/port_26_1.py` of the Midnight Lurker port and `tools/port_dv_26_1.py`); data: `tools/port_resources_26_1.py`.

- Lakes: 26.1's lake feature moves its origin by −8 itself, the 1.21 placement did the same (twice reached chunks the
  generation cannot see: a crash); the placement offset is gone. Random patches are placement modifiers (count, random
  offset, the inner filter) as vanilla's patches.
- Block and fluid layers come from the textures (no render type maps); fluid textures are fluid models.
- Saved data files are `data/the_deep_void/<name>.dat` of the dimension; a 1.21 world's file is read once from where
  1.21 kept it (`TheDeepVoidModVariables.legacyData`). Not checked on an updated world.
- Trim materials come from the items' properties (`trimMaterial`), crafting remainders and recipe results are item
  stack templates, the JEI recipe serializers are records.
- Light: 26.1 gives a dimension its minimum light by `ambient_light_color` (the mod's: `#0a0a0a` as vanilla's
  overworld); night vision is grey in 26.1, so the Deep Void looks darker than on 1.21.11 with night vision (vanilla).
- Checked: dev server (both), production server (Fabric with the jar), the world scene and the items scene on both
  loaders. Not checked: a NeoForge production server, MSPT / FPS.

## Minecraft 26.2 (branch mc/26.2, 2026-10-31)

NeoForge 26.2.0.93, Fabric API 0.161.0, GeckoLib 5.5.6, JEI 30.48. Repeatable from the originals:
`tools/port_26_2.sh` (the 26.1.2 pipeline, then modding-kb's `tools/port_262.py` and `tools/port_dv_26_2.py`); data:
`tools/port_resources_26_2.py` after the 1.21.11 and 26.1 converters.

- The screen and the HUD live in `Minecraft.gui` (`gui.screen()`, `gui.hud`); the Fabric mixins target `Hud` and
  `ItemInHandRenderer.submitArmWithItem`; NeoForge's effect text is `extractInventoryText`.
- `net.minecraft.util.Tuple` is gone: the server work queue of `TheDeepVoidMod` has its own entry class.
- Emissive blocks: `emissiveRendering` takes the block state only.
- Lakes name what they may replace (`can_place_feature`, `can_replace_with_air_or_fluid`, `can_replace_with_barrier`,
  vanilla's rules); trees and the mod's fleshy formations put their `below_trunk_provider` instead of `dirt_provider`.
- Structure features (giant skulls and the like) are clipped one block inside the zone a feature may write to: 26.2
  logs the reads of the edge blocks' neighbours outside it.
- The bone pile deltas (see «Known, unchanged from 1.20.1») now also log "Detected unsafe terrain read" (hundreds
  while exploring): 26.2 logs the reads outside the write zone as well. Kept as in the original (smaller deltas would
  change how the biomes look).
- `neoforge.mods.toml` names the logo `iconFile` (`logoFile` is deprecated and shows a warning screen).
- Checked: dev server (both), production server (Fabric with the jar), the world scene and the items scene on both
  loaders, guns in first person (the scene shows the HUD there: a hidden HUD hides the hand as well). Not checked: a
  NeoForge production server, MSPT / FPS.

## Minecraft 26.3 (branch mc/26.3, 2026-10-31)

NeoForge 26.3.0.99, Fabric API 0.165.0, GeckoLib 5.5.7, JEI 31.17. Repeatable from the originals:
`tools/port_26_3.sh` (the 26.2 pipeline, then modding-kb's `tools/port_263_input.py` and `tools/port_dv_26_3.py`);
data: `tools/port_resources_26_3.py` (the Rats port's converter, checked against vanilla 26.2 → 26.3, plus the
mod's enchantments, the noise settings inside its dimensions, the processors inside its jigsaw pools and 1.20 loot
shorthands) after the earlier converters.

- Features are their own configuration in 26.3: the mod's features (an MCreator condition before a vanilla tree,
  blob, ore or the structure feature) are records around the vanilla feature, registered as feature types; the
  configured features are `worldgen/feature`.
- Brewing: the 30 MCreator brewing classes are 50 `minecraft:brewing` recipe files (the potion recipes once per
  bottle, as vanilla's); the brewing stand takes their inputs (needles, the fishy potion) by itself.
- Sulfur burns as before through its `cooking_fuel` component (`context_int_provider/cooking/time_sulfur.json`,
  3400 ticks, shorter in the fast furnaces as vanilla's coal) and composts through its `compostable` component.
- Tools are items with the tool properties (no axe, shovel or hoe classes); the smithing templates and the rot weapons
  check the item tags instead.
- Input is SDL: the key bindings without a default are `InputConstants.UNKNOWN` (0).
- Checked: dev server (both), production server (Fabric with the jar), the world scene, the mobs and the items scene
  (guns in first person, GeckoLib armor) on both loaders. The pipeline reproduces the code from the originals
  (`tools/restore_common.sh` now brings back the packages 26.3 removes).
- Brewing in the game (`-Pscene=brewing`): every brewing recipe of the mod in its own brewing stand with blaze powder,
  the bottles compared with the recipe outputs after the brew: 50 of 50 on both loaders.
- Production server NeoForge 26.3.0.99 (installer, the mod's jar and GeckoLib 5.5.7, `prodtest/serve.sh`): Done in
  1.9 s, a minute of ticks, no errors, no "Can't keep up".
- Tick cost (`tools/bench.sh 26.3`, the bench scene of the 1.21.1 measurement: base line = the mod's tick handlers off,
  mean of 2 windows of 200 ticks per cell; Apple M4, JDK 25, dev client):

| Place | Measure | NeoForge base | NeoForge mod | Fabric base | Fabric mod |
| --- | --- | ---: | ---: | ---: | ---: |
| Overworld | player tick, server / client, µs | 11 / 7 | 102 / 73 | 11 / 9 | 105 / 80 |
| Deep Void | player tick, server / client, µs | 8 / 5 | 135 / 109 | 8 / 7 | 121 / 94 |
| Deep Void + stalkers, overseer | player tick, server / client, µs | 3 / 2 | 79 / 69 | 2 / 2 | 119 / 98 |
| all three | entity ticks, server, µs | 3–5 | 5–9 | 3–6 | 5–11 |
| all three | level ticks, µs | 0.6–0.7 | 2.5–3.6 | 0.2–0.6 | 2.6–3.2 |
| all three | MSPT, ms | 1.6–2.1 | 1.7–2.0 | 1.5–1.9 | 1.7–2.0 |
| all three | FPS | 98 / 107 / 116 | 102 / 104 / 100 | 101 / 105 / 93 | 108 / 116 / 104 |

  The handlers cost what they cost on 1.21.1 after the fixes (player tick 99 / 199 / 123 µs there); MSPT and FPS are
  within the noise of the base line. The FPS is lower than on 1.21.1 for the base line as well (the 26.x renderer in
  the dev client). The bench sets the inactivity frame limit to "minimized": with the default (AFK) the game drops
  to 30 FPS a minute after the last input. Not checked: a world played with several players.

## Known, unchanged from 1.20.1

- Rotten bone pile deltas (vanilla `delta_feature`, size up to 16 + rim up to 8) can reach past the chunks a feature may
  write to; the game drops those few blocks and logs "Detected setBlock in a far chunk". 1.20.1 did exactly the same.
