# HANDOFF — The Deep Void: NeoForge + Fabric, 1.21.11 и 26.x

Агенту: прочитай этот файл первым. Общие правила владельца — в его глобальном `CLAUDE.md` (ответы на русском,
коммиты от `Vladeezyy <vladeezym@gmail.com>` без AI-атрибуции, LGPL-3.0-only, 6 обязательных языков, тесты перед
релизом, записи в `Vladeezyy/modding-kb`).

## Задача (от владельца, 2026-10-30)
Обновить мод до **1.21.11 и всех 26.x** (26.1.2, 26.2, 26.3 — последний релиз MC на 2026-10-30; 26.4 пока снапшоты),
на **Fabric и NeoForge**.

## Состояние
- `main` — порт The Deep Void 1.99.3.1 на **NeoForge 1.21.1** + оптимизация тиковых обработчиков (`PORTLOG.md`).
- `multiloader` — **шаг 1 сделан (2026-10-30)**: 1.21.1 разделён на `common/` `neoforge/` `fabric/`; dev- и
  продакшен-серверы, сцена биомов и предметов проходят на обоих загрузчиках (раздел «NeoForge + Fabric» в `PORTLOG.md`,
  разбор `modding-kb/porting/multiloader/the-deep-void.md`). Разделение повторяемо: `tools/restore_common.sh` +
  `tools/port_multiloader.py`. Тесты: `LOADER=neoforge|fabric tools/servertest.sh`, `tools/scenetest.sh [-Pscene=items]`,
  `tools/bench.sh`.
- `mc/1.21.11` — **шаг 2 сделан (2026-10-30, локальные коммиты, не запушено)**: NeoForge 21.11.48 + Fabric 0.141.6,
  GeckoLib 5.4.5, JEI 27.54. Повторяемо: `tools/port_1_21_11.sh` (код) + `tools/port_resources_1_21_11.py` (ресурсы и
  данные). Проверено: dev-серверы обоих загрузчиков, production-сервер Fabric (`:fabric:prodServer`), сцены биомов/мобов
  и предметов на обоих загрузчиках (скриншоты в `<loader>/run/screenshots`). Не проверено: production-сервер NeoForge
  с jar, руки GeckoLib-пушек от первого лица (сцена их не показывает и на 1.21.1), замер MSPT/FPS на 1.21.11.
  Раздел «Minecraft 1.21.11» в `PORTLOG.md`, разбор `modding-kb/porting/mc-versions/1.21.11/the-deep-void.md`.
- `mc/26.1.2` — **сделано (2026-10-31, запушено)**: NeoForge 26.1.2.125 + Fabric 0.155.3, GeckoLib 5.5.2, JEI 29.53,
  Java 25, Loom 1.18. Повторяемо: `tools/port_26_1.sh` + `tools/port_resources_26_1.py` (после ресурсов 1.21.11).
  Проверено: dev-серверы обоих, production-сервер Fabric, сцены мира и предметов на обоих. Раздел «Minecraft 26.1.2» в
  `PORTLOG.md`, разбор `modding-kb/porting/mc-versions/26.1/the-deep-void.md`.
- `mc/26.2` — **сделано (2026-10-31, запушено)**: NeoForge 26.2.0.93 + Fabric 0.161.0, GeckoLib 5.5.6, JEI 30.48.
  Повторяемо: `tools/port_26_2.sh` + `tools/port_resources_26_2.py` (после ресурсов 1.21.11 и 26.1). Проверено:
  dev-серверы обоих, production-сервер Fabric, сцены мира и предметов на обоих (пушки от первого лица — теперь сцена
  включает HUD на этих кадрах). Раздел «Minecraft 26.2» в `PORTLOG.md`, разбор
  `modding-kb/porting/mc-versions/26.2/the-deep-void.md`.
- `mc/26.3` — **сделано (2026-10-31, запушено)**: NeoForge 26.3.0.99 + Fabric 0.165.0, GeckoLib 5.5.7, JEI 31.17.
  Повторяемо: `tools/port_26_3.sh` + `tools/port_resources_26_3.py` (конвертер Rats с дополнениями, после ресурсов
  предыдущих версий). Проверено: dev-серверы обоих, production-сервер Fabric, сцены мира, мобов и предметов на обоих.
  Затем (2026-10-31) проверено: зельеварка в игре (сцена `-Pscene=brewing`, 50 из 50 рецептов на обоих),
  production-сервер NeoForge 26.3.0.99 с jar (`prodtest/26.3.0.99`, `prodtest/serve.sh`), замер `tools/bench.sh 26.3`
  (MSPT/FPS в пределах шума против базы, таблица в `PORTLOG.md`). Раздел «Minecraft 26.3» в `PORTLOG.md`, разбор
  `modding-kb/porting/mc-versions/26.3/the-deep-void.md`.
- Дальше (по задаче владельца): README и релизы на версиях.

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
