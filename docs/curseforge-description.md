# The Deep Void (Unofficial Port)

**Fall through the bedrock. Something down there has been waiting.**

## 📜 Original mod

This is an unofficial port of **[The Deep Void](https://www.curseforge.com/minecraft/mc-mods/the-deep-void)** 1.99.3.1
by **Sombrius** (The Deep Void Team, Forge 1.20.1). Original source: [github.com/TroupeMaster/TheDeepVoid](https://github.com/TroupeMaster/TheDeepVoid),
license **MIT**, which allows derivative works. All the content, art, sounds and ideas are theirs; please report bugs of
this port to [this port's issues](https://github.com/Vladeezyy/the-deep-void-1.21/issues), not to the original author.

### How this port differs from the original

- **New Minecraft versions and loaders:** NeoForge **and Fabric** for Minecraft **1.21.1, 1.21.11, 26.1.2, 26.2 and
  26.3** (the original is Forge 1.20.1 only).
- **Same content and numbers:** all 991 items, 472 blocks, 193 entities, 58 effects and 17 biomes of 1.99.3.1. Where
  Minecraft 1.21+ works differently, the port keeps the 1.20.1 behaviour (enchantments, paintings and music discs as
  data with the same values, Smite / Bane of Arthropods on the mod's mobs, the arrows' knockback, the guns' arm poses).
- **Fixes:** Liquid Void / Ichor lakes no longer crash world generation on 1.21+, big structures (giant skulls) are cut
  cleanly at the chunk border.
- **Lighter ticks:** the stalker, overseer and other per-tick checks no longer search the world around every player
  every tick, and the player data is synced once a tick instead of up to 8 times. Measured against the mod switched off,
  the server's tick time stays within the noise.
- **More languages:** Russian and Chinese finished; Ukrainian, German, Spanish and Portuguese (Brazil) added; tooltips
  written in English in the original's code are translatable.
- **Minecraft 26.3:** the mod's potions are brewing recipes (data), its fuel and compost are item components.

## 🕳️ About the mod

The Deep Void adds a horror dimension beneath the bedrock: a desolate, forgotten void full of bones, flesh and things
that watch you from the dark. Explore 17 biomes, survive what lives there, uncover the lore of a lost civilisation and
fight bosses with complex attack patterns for some of the strongest gear in the game.

- **17 biomes**: bone dunes, flesh plains, graveyards, a monolith maze, the Staring Hills, the Crawler Nest, misted ruins…
- **Bosses** with their own music, arenas and loot; progression is guided by the advancements tab
- **Light is life**: the dimension's inhabitants hate light and break light sources. Stay in the dark too long and the
  Stalker comes for you
- **Hallucinations, abductions, curses, cursed treasure and secrets**, most of it configurable

## 🚪 How to get there

- **The hole**: fall into the void through the bedrock hole at 0, 0 (a structure at 0, −64, 0 makes it, even in old worlds)
- **Cracked Bedrock**: found in the Overworld; step on it and it breaks
- **The End**: falling into the void of the End also takes you down
- **Abductors**: they grab you and carry you there… crouch to escape

**Way out:** craft and use a **Void Pendant**.

## 📦 Installation

Pick the file for your Minecraft version and loader. Needed on both the client and the server.

- **Minecraft 1.21.1**: NeoForge 21.1.197+ or Fabric (Fabric API); GeckoLib 4.9+
- **Minecraft 1.21.11**: NeoForge 21.11.48+ or Fabric (Fabric API); GeckoLib 5.4.5+
- **Minecraft 26.1.2**: NeoForge 26.1.2.125+ or Fabric (Fabric API); GeckoLib 5.5.2+
- **Minecraft 26.2**: NeoForge 26.2.0.93+ or Fabric (Fabric API); GeckoLib 5.5.6+
- **Minecraft 26.3**: NeoForge 26.3.0.99+ or Fabric (Fabric API); GeckoLib 5.5.7+
- **Required:** [GeckoLib](https://www.curseforge.com/minecraft/mc-mods/geckolib) (and [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) on Fabric)
- **Optional:** [JEI](https://www.curseforge.com/minecraft/mc-mods/jei) (the mod's recipe categories)

Settings: `config/Deep Void Config.toml`.

## 🔗 Links

- Source code, full changelog and the reasons for every change: [github.com/Vladeezyy/the-deep-void-1.21](https://github.com/Vladeezyy/the-deep-void-1.21)
  ([PORTLOG](https://github.com/Vladeezyy/the-deep-void-1.21/blob/mc/26.3/PORTLOG.md))
- Bugs of this port: [issues](https://github.com/Vladeezyy/the-deep-void-1.21/issues)

## ⚖️ License

This port: **LGPL-3.0-only**, Copyright (c) 2026 Vladeezyy. The original The Deep Void is under the **MIT License**
(Copyright (c) 2025 Sombrius); its text is kept in the port's `licenses/LICENSE-MIT`.
