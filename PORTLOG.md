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

## Known, unchanged from 1.20.1

- Rotten bone pile deltas (vanilla `delta_feature`, size up to 16 + rim up to 8) can reach past the chunks a feature may
  write to; the game drops those few blocks and logs "Detected setBlock in a far chunk". 1.20.1 did exactly the same.
