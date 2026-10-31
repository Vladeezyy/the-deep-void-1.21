<div align="center">

<img src="docs/images/logo.png" width="160" alt="The Deep Void logo">

# The Deep Void — NeoForge & Fabric, Minecraft 1.21.1 – 26.3

**Fall through the bedrock. Something down there has been waiting.**

[![Minecraft 1.21.1 · 1.21.11 · 26.1.2 · 26.2 · 26.3](https://img.shields.io/badge/Minecraft-1.21.1%20%C2%B7%201.21.11%20%C2%B7%2026.1.2%20%C2%B7%2026.2%20%C2%B7%2026.3-3c8527)](https://www.minecraft.net)
[![NeoForge](https://img.shields.io/badge/NeoForge-e0682b)](https://neoforged.net)
[![Fabric](https://img.shields.io/badge/Fabric-dbd0b4)](https://fabricmc.net)
[![requires GeckoLib](https://img.shields.io/badge/requires-GeckoLib-8a2be2)](https://modrinth.com/mod/geckolib)
[![License LGPL-3.0](https://img.shields.io/badge/license-LGPL--3.0-blue)](LICENSE)

An unofficial port of **[The Deep Void](https://www.curseforge.com/minecraft/mc-mods/the-deep-void)** v1.99.3.1
by **Sombrius** from Forge 1.20.1 to **NeoForge and Fabric** for Minecraft **1.21.1, 1.21.11, 26.1.2, 26.2 and 26.3**,
with all of its content.

</div>

---

## 🕳️ About the mod

The Deep Void adds a horror dimension beneath the bedrock: a desolate, forgotten void full of bones, flesh and things
that watch you from the dark. Explore 17 biomes, survive what lives there, uncover the lore of a lost civilisation and
fight bosses with complex attack patterns for some of the strongest gear in the game.

- **991 items, 472 blocks, 193 entities, 58 effects** — everything from the original 1.99.3.1
- **17 biomes** — bone dunes, flesh plains, graveyards, a monolith maze, the Staring Hills, the Crawler Nest, misted ruins…
- **Bosses** with their own music, arenas and loot — progression is guided by the advancements tab
- **Light is life** — the dimension's inhabitants hate light and break light sources. Stay in the dark too long and the
  Stalker comes for you
- **Hallucinations, abductions, curses, cursed treasure and secrets** — most of it configurable

## 🚪 How to get there

| Way in | |
| --- | --- |
| **The hole** | Fall into the void through the bedrock hole at **0, 0** (a structure at 0, −64, 0 makes it, even in old worlds) |
| **Cracked Bedrock** | Found in the Overworld — step on it and it breaks |
| **The End** | Falling into the void of the End also takes you down |
| **Abductors** | They grab you and carry you there… crouch to escape |

**Way out:** craft and use a **Void Pendant**.

## 📸 Screenshots

<p align="center">
  <img src="docs/images/mobs.jpg" width="49%" alt="Inhabitants of the Deep Void">
  <img src="docs/images/gloomy_deathgrounds.jpg" width="49%" alt="Gloomy Deathgrounds">
  <img src="docs/images/misted_remnants.jpg" width="49%" alt="Misted Remnants">
  <img src="docs/images/staring_hills.jpg" width="49%" alt="Staring Hills">
</p>

## 📦 Requirements

One jar per Minecraft version and loader ([releases](../../releases)): `the-deep-void-<loader>-1.99.3.1.1+<minecraft>.jar`.

- **Minecraft 1.21.1** — NeoForge 21.1.197+ or Fabric Loader 0.19.5+ with Fabric API; GeckoLib 4.9+.
- **Minecraft 1.21.11** — NeoForge 21.11.48+ or Fabric with Fabric API; GeckoLib 5.4.5+.
- **Minecraft 26.1.2** — NeoForge 26.1.2.125+ or Fabric with Fabric API; GeckoLib 5.5.2+; Java 25.
- **Minecraft 26.2** — NeoForge 26.2.0.93+ or Fabric with Fabric API; GeckoLib 5.5.6+; Java 25.
- **Minecraft 26.3** — NeoForge 26.3.0.99+ or Fabric with Fabric API; GeckoLib 5.5.7+; Java 25.
- **[GeckoLib](https://modrinth.com/mod/geckolib)** — the build for your loader and Minecraft version.

Needed on both the client and the server. **JEI** is optional (custom recipe categories).
Settings: `config/Deep Void Config.toml` (or in game with the Configured mod).

## 🌍 Languages

English · Русский · Українська · Deutsch · 简体中文 · Español · Português (Brasil)

The Russian and Chinese translations were finished and Ukrainian, German, Spanish and Portuguese were added in this
port. Tooltips that
the original wrote in English straight in the code are translatable too.

## 🔧 What changed in the port

The content and the numbers are the same as in 1.20.1. Where Minecraft 1.21 works differently, the port keeps the
1.20.1 behaviour:

- Enchantments, paintings and music discs are 1.21 data (same costs, sizes, sounds and lengths).
- Undead / arthropod mobs are in the 1.21 entity tags, so Smite and Bane of Arthropods work as before.
- Arrows of the mod's weapons keep their own knockback; the guns keep their arm poses.
- World generation fixes: lakes of Liquid Void / Ichor no longer crash chunk generation on 1.21, big structures (giant
  skulls) are cut cleanly at the chunk border instead of spamming the log.
- **Fabric**: the same mod on Fabric (NeoForge's events, fluids, brewing and arm poses through Fabric API and mixins).
- **Lighter ticks**: the stalker, overseer and other per-tick checks no longer scan the world around each player every
  tick, and the player data is synced once a tick instead of up to 8 times (measured against the mod switched off: the
  server's tick time is within the noise).
- **Minecraft 1.21.11 and 26.x**: GeckoLib 5, the new rendering, world generation and data formats; on 26.3 the mod's
  potions are brewing recipes and its fuel and compost are item components. The Deep Void looks darker with night vision
  on 26.x: that is vanilla's grey night vision.

The full list (with the reasons) is in **[PORTLOG.md](PORTLOG.md)**.

## ❤️ Credits

- **The Deep Void** — made by **Sombrius** (The Deep Void Team) with MCreator ·
  [CurseForge](https://www.curseforge.com/minecraft/mc-mods/the-deep-void) ·
  [source & wiki](https://github.com/TroupeMaster/TheDeepVoid). All the content, art, sounds and ideas are theirs.
- **Port** to NeoForge and Fabric, Minecraft 1.21.1 – 26.3 — [Vladeezyy](https://github.com/Vladeezyy).
- Bugs of **this port**: [issues](../../issues). Please don't report port bugs to the original author.

This port: **LGPL-3.0-only** ([LICENSE](LICENSE), GPL-3.0 text in [COPYING](licenses/COPYING)), Copyright (c) 2026 Vladeezyy. The original The Deep Void is under the MIT License; its text and copyright notice are kept in [LICENSE-MIT](licenses/LICENSE-MIT). Forks and modified versions must stay open under the same license and credit the authors. Please download the mod only from the official release pages.

---

<details>
<summary><b>🇷🇺 Описание на русском</b></summary>

<br>

**Провалитесь сквозь бедрок. Там, внизу, вас уже ждут.**

Неофициальный порт мода **[The Deep Void](https://www.curseforge.com/minecraft/mc-mods/the-deep-void)** v1.99.3.1
от **Sombrius** с Forge 1.20.1 на **NeoForge и Fabric** для Minecraft **1.21.1, 1.21.11, 26.1.2, 26.2 и 26.3** — со всем
содержимым.

<details>
<summary><b>🕳️ О моде</b></summary>

The Deep Void добавляет хоррор-измерение под бедроком: заброшенную, забытую пустоту из костей, плоти и того, что следит
за вами из темноты. Исследуйте 17 биомов, выживите среди местных обитателей, раскройте историю исчезнувшей цивилизации и
сразитесь с боссами со сложными атаками ради одного из самых сильных снаряжений в игре.

- **991 предмет, 472 блока, 193 сущности, 58 эффектов** — всё из оригинальной версии 1.99.3.1
- **17 биомов** — костяные дюны, равнины плоти, кладбища, лабиринт монолитов, Взирающие холмы, Гнездо ползунов, туманные руины…
- **Боссы** со своей музыкой, аренами и добычей — путь подсказывает вкладка достижений
- **Свет — это жизнь**: обитатели измерения ненавидят свет и ломают его источники. Задержитесь в темноте слишком
  долго — и за вами придёт Сталкер
- **Галлюцинации, похищения, проклятия, проклятые сокровища и секреты** — почти всё настраивается

</details>

<details>
<summary><b>🚪 Как туда попасть</b></summary>

| Способ | |
| --- | --- |
| **Дыра** | Упасть в пустоту через дыру в бедроке на **0, 0** (её делает структура на 0, −64, 0 — даже в старых мирах) |
| **Треснувший бедрок** | Встречается в Верхнем мире — наступите, и он сломается |
| **Энд** | Падение в пустоту Энда тоже ведёт вниз |
| **Похитители** | Схватят и унесут… присядьте, чтобы вырваться |

**Выход:** скрафтите и используйте **Кулон пустоты** (Void Pendant).

</details>

<details>
<summary><b>📦 Требования</b></summary>

Один jar на версию Minecraft и загрузчик ([релизы](../../releases)): `the-deep-void-<загрузчик>-1.99.3.1.1+<minecraft>.jar`.

- **Minecraft 1.21.1** — NeoForge 21.1.197+ или Fabric Loader 0.19.5+ с Fabric API; GeckoLib 4.9+.
- **Minecraft 1.21.11** — NeoForge 21.11.48+ или Fabric с Fabric API; GeckoLib 5.4.5+.
- **Minecraft 26.1.2** — NeoForge 26.1.2.125+ или Fabric с Fabric API; GeckoLib 5.5.2+; Java 25.
- **Minecraft 26.2** — NeoForge 26.2.0.93+ или Fabric с Fabric API; GeckoLib 5.5.6+; Java 25.
- **Minecraft 26.3** — NeoForge 26.3.0.99+ или Fabric с Fabric API; GeckoLib 5.5.7+; Java 25.
- **[GeckoLib](https://modrinth.com/mod/geckolib)** — сборка под ваш загрузчик и версию Minecraft.

Нужен и на клиенте, и на сервере. **JEI** — по желанию (свои категории рецептов).
Настройки: `config/Deep Void Config.toml` (или в игре через мод Configured).

</details>

<details>
<summary><b>🌍 Языки</b></summary>

English · Русский · Українська · Deutsch · 简体中文 · Español · Português (Brasil)

В порте русский и китайский переводы доведены до конца, добавлены украинский, немецкий, испанский и
португальский. Подсказки, которые в
оригинале были написаны по-английски прямо в коде, тоже переводятся.

</details>

<details>
<summary><b>🔧 Что изменилось при портировании</b></summary>

Содержимое и все числа — как в 1.20.1. Там, где Minecraft 1.21 устроен иначе, порт сохраняет поведение 1.20.1:

- Зачарования, картины и музыкальные диски стали данными 1.21 (те же стоимости, размеры, звуки и длительность).
- Нежить и членистоногие мода — в тегах сущностей 1.21, поэтому «Небесная кара» и «Бич членистоногих» работают как раньше.
- Стрелы оружия мода сохранили свою отдачу, у пушек остались свои позы рук.
- Исправления генерации мира: озёра Жидкой пустоты / Ихора больше не роняют генерацию чанков на 1.21, большие
  структуры (гигантские черепа) аккуратно обрезаются по границе чанка вместо сотен ошибок в логе.
- **Fabric**: тот же мод на Fabric (события NeoForge, жидкости, зельеварение и позы рук — через Fabric API и миксины).
- **Лёгкие тики**: проверки сталкера, надзирателя и прочие больше не обыскивают мир вокруг каждого игрока каждый тик,
  а данные игрока синхронизируются раз в тик вместо 8 раз (замер против выключенного мода: время тика сервера — в
  пределах шума).
- **Minecraft 1.21.11 и 26.x**: GeckoLib 5, новый рендер, генерация мира и форматы данных; на 26.3 зелья мода —
  рецепты зельеварки, а топливо и компост — компоненты предметов. С ночным зрением Пустота на 26.x темнее — это
  ванильное серое ночное зрение.

Полный список (с причинами) — в **[PORTLOG.md](PORTLOG.md)**.

</details>

<details>
<summary><b>❤️ Авторы</b></summary>

- **The Deep Void** — автор **Sombrius** (The Deep Void Team), сделан в MCreator ·
  [CurseForge](https://www.curseforge.com/minecraft/mc-mods/the-deep-void) ·
  [исходники и вики](https://github.com/TroupeMaster/TheDeepVoid). Всё содержимое, графика, звуки и идеи — их.
- **Порт** на NeoForge и Fabric, Minecraft 1.21.1 – 26.3 — [Vladeezyy](https://github.com/Vladeezyy).
- Ошибки **этого порта** — в [issues](../../issues). Пожалуйста, не отправляйте ошибки порта автору оригинала.

Этот порт: **LGPL-3.0-only** ([LICENSE](LICENSE), текст GPL-3.0 в [COPYING](licenses/COPYING)), Copyright (c) 2026 Vladeezyy. Оригинальный The Deep Void — под лицензией MIT, её текст и копирайт сохранены в [LICENSE-MIT](licenses/LICENSE-MIT). Форки и изменённые версии обязаны оставаться открытыми под той же лицензией и указывать авторов. Пожалуйста, скачивайте мод только со страниц официальных релизов.

</details>

</details>
