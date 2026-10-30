# HANDOFF — The Deep Void: NeoForge + Fabric, 1.21.11 и 26.x

Агенту: прочитай этот файл первым. Общие правила владельца — в его глобальном `CLAUDE.md` (ответы на русском,
коммиты от `Vladeezyy <vladeezym@gmail.com>` без AI-атрибуции, LGPL-3.0-only, 6 обязательных языков, тесты перед
релизом, записи в `Vladeezyy/modding-kb`).

## Задача (от владельца, 2026-10-30)
Обновить мод до **1.21.11 и всех 26.x** (26.1.2, 26.2, 26.3 — последний релиз MC на 2026-10-30; 26.4 пока снапшоты),
на **Fabric и NeoForge**.

## Состояние
- `main` — порт The Deep Void 1.99.3.1 на **NeoForge 1.21.1** (готов, см. `PORTLOG.md`, `README.md`).
- `multiloader` — ветка этой работы; пока только этот файл, код не менялся.

## Масштаб (замерено)
- 3108 Java-файлов, ~206 тыс. строк (MCreator), 1643 импорта GeckoLib, 36 — JEI.
- NeoForge API: ~55 типов событий в `@SubscribeEvent` (самые частые: `ItemTooltipEvent` 59, `LivingIncomingDamageEvent` 49,
  `RegisterEvent` 35, `PlayerTickEvent.Post` 31, `RegisterBrewingRecipesEvent` 30, `FMLCommonSetupEvent` 20,
  `LivingDeathEvent` 13, `ModifyDefaultComponentsEvent` 11, `RenderGuiEvent.Pre` 10, `ViewportEvent.*` 15).
- Прочее NeoForge-only: `DeferredSoundType` (62), `NeoForgeMod` (44), `IBrewingRecipe` (30), item handlers /
  `SidedInvWrapper` / `Capabilities` (~50), `FluidType` + `IClientFluidTypeExtensions` (жидкости Liquid Void / Ichor),
  `IClientItemExtensions` (14), `IClientMobEffectExtensions` (11), `RegisterDimensionSpecialEffectsEvent` (небо),
  арм-позы через `enumextensions.json` (`port/ArmPoses`), `ModConfigSpec`, `FakePlayerFactory`, payload-сеть (17 сообщений).

## План (как у The Midnight Lurker — тот же MCreator-подход)
Образец: `Vladeezyy/the-midnight-lurker-1.21` (ветки `main`, `mc/1.21.11`, `mc/26.1.2`, `mc/26.2`, `mc/26.3`; NeoForge + Fabric).
Разбор в базе: `modding-kb/porting/multiloader/midnight-lurker.md`, `porting/mcreator.md`, `conventions/multi-version.md`.

1. **Разделение на 1.21.1 (эта ветка → `main`)**: `common/` + `neoforge/` + `fabric/` по шаблону ML
   (`build.gradle`, `settings.gradle`, `buildSrc/multiloader-*.gradle`, Loom 1.17 + ModDevGradle 2.0.148).
   - Слой `port/` из ML (`port/bus/*` — классы событий с именами NeoForge, `LurkerBus` → своя шина,
     `port/platform/Platform|Registrar|RegistryEntry`) переименовать в `net.sombrius.thedeepvoid.port`.
   - `tools/port_multiloader.py` из ML адаптировать (пакет, `TheDeepVoidModVariables`, `TheDeepVoidModMobEffects`)
     и прогнать по `common/`; он генерирует `EventIndex`/`ClientEventIndex`.
   - Дописать классы шины и мосты (NeoForge-слушатели + Fabric API/миксины) для событий, которых нет в ML:
     `ItemTooltipEvent`, `RegisterBrewingRecipesEvent`, `ModifyDefaultComponentsEvent`, `LivingFallEvent`,
     `BlockEvent.*`, `LivingChangeTargetEvent`, `LivingShieldBlockEvent`, `LivingHealEvent`, `MobEffectEvent.*`,
     `CanPlayerSleepEvent`, `PlayerXpEvent`, `ServerChatEvent`, `EntityTravelToDimensionEvent`,
     `FurnaceFuelBurnTimeEvent`, `ItemAttributeModifierEvent`, `LevelTickEvent`, `LevelEvent.Load`,
     `RegisterDimensionSpecialEffectsEvent`, `RegisterKeyMappingsEvent`, `RegisterClientExtensionsEvent`.
   - Fabric-замены: жидкости без `FluidType`, зелья из `IBrewingRecipe` (`FabricBrewingRecipeRegistryBuilder`
     или миксин), арм-позы (миксин вместо enum extension), конфиг (`ModConfigSpec` → свой или Forge Config API Port).
   - Тесты: сервер стартует на обоих, сцена (`port/DevScene`) на обоих, prod-сервер Fabric.
2. **`mc/1.21.11`** от `main`: скрипты ML `port_1_21_11.py`, `port_geckolib5.py`, `convert_resources_1_21_11.py`
   (в ML они на ветках `mc/*`, `tools/`) + `modding-kb/porting/mc-versions/1.21.11.md`, `apis/geckolib.md` (GeckoLib 5).
3. **`mc/26.1.2`, `mc/26.2`, `mc/26.3`** — цепочкой: `port_26_1.py`, `port_26_3.py`, `convert_resources_26_3.py` из ML;
   GeckoLib 5.5 только на Modrinth Maven; сборка без маппингов (`modding-kb/build/unobfuscated-26x.md`).
   Версии на 2026-10-30: NeoForge 26.1.2.125, 26.3.0.99; Fabric Loader 0.20.1.
4. На каждой версии: тесты (функц., стресс, совместимость), README, GitHub Release (англ.), запись в modding-kb.

## Где что лежит (ПК владельца, Windows)
- Репо: `D:\mods\the-deep-void-1.21`.
- Клон ML со всеми ветками: `D:\mods\_ref\midnight-lurker`.
- Исходники MC/NeoForge для сверки API: `D:\mods\_ref\mc1211src`, `mc2612src`, `mc262src`, `mc263src`, `neo2612src`.
- На MacBook: `~/Desktop/the deep void 1.21+` (по `modding-kb/projects/index.md`); там склонировать ML заново.
