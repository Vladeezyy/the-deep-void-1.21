# HANDOFF — The Deep Void: NeoForge + Fabric, 1.21.1 – 26.3

Агенту: прочитай этот файл первым. Общие правила владельца — в его глобальном `CLAUDE.md` (ответы на русском,
коммиты от `Vladeezyy <vladeezym@gmail.com>` без AI-атрибуции, LGPL-3.0-only, 6 обязательных языков, тесты перед
релизом, записи в `Vladeezyy/modding-kb`). Этот файл одинаковый во всех ветках.

## Состояние (2026-11-03)
Задача владельца от 2026-10-30 («1.21.11 и все 26.x, Fabric + NeoForge») **выполнена и выпущена**.

| Ветка | MC | NeoForge | Fabric API | GeckoLib | Java | Последний релиз |
| --- | --- | --- | --- | --- | --- | --- |
| `multiloader` | 1.21.1 | 21.1.259 (от 21.1.197) | 0.116.17 | 4.9.3 | 21 | `v1.99.3.1.3+1.21.1` |
| `mc/1.21.11` | 1.21.11 | 21.11.48 | 0.141.6 | 5.4.5 | 21 | `v1.99.3.1.2+1.21.11` |
| `mc/26.1.2` | 26.1.2 | 26.1.2.125 | 0.155.3 | 5.5.2 | 25 | `v1.99.3.1.2+26.1.2` |
| `mc/26.2` | 26.2 | 26.2.0.93 | 0.161.0 | 5.5.6 | 25 | `v1.99.3.1.2+26.2` |
| `mc/26.3` | 26.3 | 26.3.0.99 | 0.165.0 | 5.5.7 | 25 | `v1.99.3.1.2+26.3` |

Fabric Loader 0.19.5 везде. GeckoLib 5.5 для 26.x — с Modrinth Maven (в `gradle.properties` id версии, а не номер).
Каждый релиз — два jar (NeoForge и Fabric) из зелёного CI (`.github/workflows/build.yml`: сборка + запуск серверов
обоих загрузчиков на каждый push).

- **`main`** — намеренно остаётся NeoForge-оригиналом 1.21.1 (с оптимизацией тиковых обработчиков): из него
  `tools/restore_common.sh main` берёт исходники для конвейера разделения. В `main` ничего не сливать из `multiloader`.
  1.21.1 для игроков выходит из `multiloader`.
- **Ветки `mc/*` не сливаются друг в друга и в `multiloader`**: каждая сделана от разделённого 1.21.1 своим конвейером.
  Исправление, нужное на нескольких версиях, переносится в каждую ветку отдельно (как фикс клавиш 1.99.3.1.2).
- Репозиторий публичный. CurseForge: **The Deep Void (Unofficial Port), проект 1775380**, описание —
  `docs/curseforge-description.md`, загрузка — `~/Desktop/modrinth-publish/cf_ports.py dv --tag <тег> --go` (MacBook);
  номера файлов — в `modding-kb/conventions/curseforge.md`.

### Ревизии порта (пятая цифра версии)
- **1.99.3.1.1** (2026-10-31) — первый выпуск NeoForge + Fabric на всех пяти версиях.
- **1.99.3.1.2** (2026-10-31, все версии) — клавиши мода и кнопки GUI больше не роняют игру
  (`Payload the_deep_void:key_* may not be sent to the server`: после разделения сообщения не регистрировались).
  Сцена `-Pscene=keys` нажимает все клавиши.
- **1.99.3.1.3** (2026-11-01, только 1.21.1) — оружие в руке роняло игру (`IllegalStateException: Not building!`,
  issue #1, закрыт): руки над костями пушки рисуются из своего буфера (`utils/AnimUtils`). На 1.21.11+ руки рисуются
  через GeckoLib 5 по-другому, этого кода там нет.

## Как работать
- Конвейеры повторяемы от оригиналов: 1.21.1 — `tools/restore_common.sh` + `tools/port_multiloader.py`;
  дальше в своей ветке `tools/port_1_21_11.sh` + `tools/port_resources_1_21_11.py`, `tools/port_26_1.sh` +
  `port_resources_26_1.py`, `tools/port_26_2.sh` + `port_resources_26_2.py`, `tools/port_26_3.sh` +
  `port_resources_26_3.py` (каждая версия запускает свои скрипты после скриптов предыдущих).
- Тесты: `LOADER=neoforge|fabric tools/servertest.sh` (dev-сервер), `tools/scenetest.sh` (сцена мира и мобов;
  `-Pscene=items` — пушки, броня, крылья; `-Pscene=keys`; на 26.3 `-Pscene=brewing`), `tools/bench.sh` (цена тиковых
  обработчиков против базы), `:fabric:prodServer` (production-сервер Fabric с jar); production-сервер NeoForge — `prodtest/serve.sh`
  (папка в `.gitignore`, в git её нет: на машине без неё поставить сервер NeoForge установщиком и положить jar сам).
  Все автозапуски — через `tools/watchdog.sh`.
- Описание изменений и отклонений от оригинала — `PORTLOG.md` (разделы по версиям в соответствующих ветках),
  разборы — `modding-kb/porting/multiloader/the-deep-void.md` и `modding-kb/porting/mc-versions/<версия>/the-deep-void.md`.

## Не проверено
- Production-сервер NeoForge с jar на 1.21.11, 26.1.2, 26.2 (на 1.21.1 и 26.3 проверен).
- Руки пушек от первого лица на 1.21.11 и 26.1.2 (на 26.2 и 26.3 сцена снимает их с HUD и проверяет).
- MSPT/FPS на 1.21.11, 26.1.2, 26.2 (замерено на 1.21.1 и 26.3).
- Чтение мира, обновлённого с 1.21.11 на 26.1.2 (`TheDeepVoidModVariables.legacyData`).
- Игра в мире с несколькими игроками.

## Где что лежит
- ПК (Windows): репо `D:\mods\the-deep-void-1.21`; клон The Midnight Lurker (образец схемы) `D:\mods\_ref\midnight-lurker`;
  исходники MC/NeoForge для сверки API `D:\mods\_ref\mc1211src`, `mc2612src`, `mc262src`, `mc263src`, `neo2612src`.
- MacBook: `~/Desktop/the deep void 1.21+`.
