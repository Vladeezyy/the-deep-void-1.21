package net.sombrius.thedeepvoid.port;

import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.ClientTickEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.Dist;

import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.Screenshot;
import net.minecraft.client.Minecraft;

import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

import com.mojang.datafixers.util.Pair;

/**
 * Development scene, inert unless the {@code deepvoid.scene} system property is set ({@code ./gradlew runScene}): creates or
 * opens the world {@code dv_scene} (seed 1234), visits Deep Void biomes, spawns some of the mod's mobs and saves
 * {@code run/screenshots/scene_*.png}, then quits. With {@code -Pscene=lang} it only prints some of the mod's translated texts
 * to the chat and saves {@code scene_lang_<language>.png}. With {@code -Pscene=bench} it measures the tick cost of the mod's
 * tick handlers ({@link PortBench}, {@code tools/bench.sh}). With {@code -Pscene=regress} it checks fixed issues: #3 (entering
 * or leaving the Deep Void showed the End Poem) and #2 (the mod's armor trims), #4 (the mod's shields block), {@code [scene] CHECK} lines in the log.
 */
@EventBusSubscriber(modid = TheDeepVoidMod.MODID, value = Dist.CLIENT)
public final class DevScene {
	private static final boolean ENABLED = System.getProperty("deepvoid.scene") != null;
	private static final boolean LANG_ONLY = "lang".equals(System.getProperty("deepvoid.scene"));
	private static final List<String> LANG_KEYS = List.of("item.the_deep_void.void_pendant", "item.the_deep_void.void_pendant.descr1", "biome.the_deep_void.misted_remnants",
			"entity.the_deep_void.stalker", "text.the_deep_void.port.the_stalker_is_after_you", "text.the_deep_void.port.press_shift_to_show_abilities", "effect.the_deep_void.hexed.description",
			"entity.the_deep_void.undertaker.canopy1");
	private static final boolean BENCH = "bench".equals(System.getProperty("deepvoid.scene"));
	private static final boolean ITEMS = "items".equals(System.getProperty("deepvoid.scene"));
	private static final boolean KEYS = "keys".equals(System.getProperty("deepvoid.scene"));
	private static final boolean REGRESS = "regress".equals(System.getProperty("deepvoid.scene"));
	private static final String WORLD = LANG_ONLY ? "dv_lang" : BENCH ? "dv_bench" : ITEMS ? "dv_items" : REGRESS ? "dv_regress" : "dv_scene";
	private static final ResourceKey<Level> DEEP_VOID = ResourceKey.create(Registries.DIMENSION, Identifier.parse("the_deep_void:deep_void"));
	private static final List<Pair<Integer, Consumer<Minecraft>>> STEPS = new ArrayList<>();
	private static boolean opened;
	private static int menuTicks;
	private static int tick;

	static {
		at(40, mc -> server(mc, p -> {
			p.setGameMode(GameType.CREATIVE);
			p.getAbilities().flying = true;
			p.onUpdateAbilities();
			p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1000000, 0, false, false));
			MinecraftServer server = p.level().getServer();
			p.level().getGameRules().set(GameRules.SPAWN_MOBS, false, server);
		}));
		if (KEYS) {
			at(50, DevScene::pressKeys);
			at(90, Minecraft::stop);
		} else if (ITEMS) {
			items();
		} else if (REGRESS) {
			regress();
		} else if (BENCH) {
			PortBench.register();
			bench();
		} else if (LANG_ONLY) {
			at(100, mc -> server(mc, p -> LANG_KEYS.forEach(key -> p.sendSystemMessage(Component.translatable(key)))));
			at(140, mc -> shot(mc, "lang_" + mc.options.languageCode));
			at(160, Minecraft::stop);
		} else {
			biomesAndMobs();
		}
	}

	private static void biomesAndMobs() {
		at(45, mc -> mc.options.hideGui = true);
		at(50, DevScene::pressKeys);
		int t = 200;
		for (String biome : List.of("forgotten_valley", "grim_canopy", "crawler_nest", "ashen_crags", "misted_remnants", "staring_hills", "gloomy_deathgrounds")) {
			at(t, mc -> server(mc, p -> go(p, biome)));
			at(t + 160, mc -> server(mc, p -> settle(p)));
			at(t + 220, mc -> shot(mc, biome));
			t += 240;
		}
		at(t, mc -> server(mc, DevScene::mobs));
		at(t + 80, mc -> shot(mc, "mobs"));
		at(t + 100, mc -> server(mc, p -> go(p, "misted_remnants")));
		at(t + 260, mc -> server(mc, DevScene::settle));
		at(t + 300, mc -> server(mc, DevScene::mobs2));
		at(t + 380, mc -> shot(mc, "mobs2"));
		at(t + 400, Minecraft::stop);
	}

	/**
	 * Three places, each with 100 pigs without AI around a survival player: the overworld, the Forgotten Valley of the Deep
	 * Void, and the same with a stalker, a watching stalker, an overseer and a void dweller behind the player (the handlers
	 * that look for them then run their inner loops). Each place is measured 4 times for 200 ticks, alternating the base
	 * line (the mod's tick handlers off the event bus) and the mod, then once more with each player tick handler timed on its own.
	 */
	private static void bench() {
		at(41, mc -> {
			mc.options.framerateLimit().set(260);
			mc.options.enableVsync().set(false);
			mc.options.renderDistance().set(12);
			mc.options.simulationDistance().set(10);
		});
		int t = 100;
		t = benchPhase(t, "overworld", p -> p.teleportTo(p.level().getServer().overworld(), 0.5, 200, 0.5, java.util.Set.of(), 0F, 20F, true), false);
		t = benchPhase(t, "deep_void", p -> go(p, "forgotten_valley"), false);
		t = benchPhase(t, "deep_void_mobs", p -> go(p, "forgotten_valley"), true);
		at(t, mc -> PortBench.summary());
		at(t + 20, Minecraft::stop);
	}

	private static int benchPhase(int t, String phase, Consumer<ServerPlayer> place, boolean modMobs) {
		at(t, mc -> server(mc, p -> {
			PortBench.setModTickHandlers(true);
			p.setGameMode(GameType.CREATIVE);
			p.getAbilities().flying = true;
			p.onUpdateAbilities();
			place.accept(p);
		}));
		at(t + 160, mc -> server(mc, DevScene::settle));
		at(t + 200, mc -> server(mc, p -> {
			ServerLevel level = p.level();
			for (Entity e : level.getEntities((Entity) null, p.getBoundingBox().inflate(128), e -> !(e instanceof Player)))
				e.discard();
			p.setGameMode(GameType.SURVIVAL);
			p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 1000000, 4, false, false));
			p.addEffect(new MobEffectInstance(MobEffects.SATURATION, 1000000, 0, false, false));
			level.getGameRules().set(GameRules.ADVANCE_TIME, false, level.getServer());
			command(level.getServer(), "time set 6000");
			for (int i = 0; i < 100; i++) {
				double angle = i * 2.4, r = 8 + (i % 17);
				spawnNoAi(level, EntityType.PIG, p.getX() + Math.cos(angle) * r, p.getZ() + Math.sin(angle) * r);
			}
			if (modMobs) {
				int i = 0;
				for (String id : List.of("stalker", "watching_stalker", "overseer", "void_dweller")) {
					double angle = Math.toRadians(p.getYRot() - 90 - 30 + i++ * 20); // behind the player, out of its sight
					spawnNoAi(level, BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, id)), p.getX() + Math.cos(angle) * 12,
							p.getZ() + Math.sin(angle) * 12);
				}
			}
		}));
		t += 600; // the biome search and the new chunks hold the server up to ~15 s after the teleport
		for (int k = 0; k < 4; k++) {
			boolean mod = k % 2 == 1;
			at(t, mc -> server(mc, p -> {
				PortBench.setModTickHandlers(mod);
				TheDeepVoidModVariables.of(p).StalkerCount = 0; // no stalker spawns during the windows
			}));
			at(t + 60, mc -> server(mc, p -> PortBench.begin()));
			at(t + 260, mc -> server(mc, p -> PortBench.end(phase)));
			t += 280;
		}
		at(t, mc -> server(mc, p -> PortBench.setProfiling(true)));
		at(t + 60, mc -> server(mc, p -> PortBench.begin()));
		at(t + 250, mc -> {
			TheDeepVoidMod.LOGGER.info("[bench] index phase={} client {}", phase, PortEntities.check(mc.level, mc.player.getX(), mc.player.getY(), mc.player.getZ()));
			server(mc, p -> TheDeepVoidMod.LOGGER.info("[bench] index phase={} server {}", phase, PortEntities.check(p.level(), p.getX(), p.getY(), p.getZ())));
		});
		at(t + 260, mc -> server(mc, p -> {
			PortBench.end(phase);
			PortBench.setProfiling(false);
		}));
		return t + 280;
	}

	private static void spawnNoAi(ServerLevel level, EntityType<?> type, double x, double z) {
		BlockPos pos = BlockPos.containing(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) Math.floor(x), (int) Math.floor(z)), z);
		if (type.spawn(level, pos, EntitySpawnReason.COMMAND) instanceof Mob mob) {
			mob.setNoAi(true);
			mob.setPersistenceRequired();
		}
	}

	/**
	 * The items with client extensions (gun renderers and arm poses, armor models, GeckoLib wings), in first and third
	 * person: {@code scene_item_<name>_1.png} / {@code _3.png}.
	 */
	private static void items() {
		at(45, mc -> mc.options.hideGui = true);
		at(60, mc -> server(mc, p -> {
			p.teleportTo(p.level().getServer().overworld(), 0.5, 120, 0.5, java.util.Set.of(), 0F, 0F, true);
			p.level().setBlock(BlockPos.containing(0, 118, 0), net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), 3);
			p.getAbilities().flying = false;
			p.onUpdateAbilities();
			command(p.level().getServer(), "time set 6000");
		}));
		int t = 140;
		for (String id : List.of("heresy", "double_barrel_shotgun", "eldritch_maw", "misanthropy")) {
			at(t, mc -> server(mc, p -> p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, new net.minecraft.world.item.ItemStack(item(id)))));
			at(t + 30, mc -> mc.options.setCameraType(net.minecraft.client.CameraType.FIRST_PERSON));
			at(t + 40, mc -> shot(mc, "item_" + id + "_1"));
			at(t + 50, mc -> mc.options.setCameraType(net.minecraft.client.CameraType.THIRD_PERSON_FRONT));
			at(t + 60, mc -> shot(mc, "item_" + id + "_3"));
			t += 80;
		}
		at(t, mc -> server(mc, p -> {
			p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, net.minecraft.world.item.ItemStack.EMPTY);
			p.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, new net.minecraft.world.item.ItemStack(item("hunters_helmet")));
			p.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, new net.minecraft.world.item.ItemStack(item("soul_wings_chestplate")));
		}));
		at(t + 40, mc -> shot(mc, "item_armor_3"));
		at(t + 50, mc -> server(mc, p -> {
			p.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, new net.minecraft.world.item.ItemStack(item("vulture_helmet")));
			p.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, new net.minecraft.world.item.ItemStack(item("fly_wings_chestplate")));
		}));
		at(t + 90, mc -> shot(mc, "item_armor2_3"));
		at(t + 110, Minecraft::stop);
	}

	/** Times the End Poem (WinScreen) was open at the end of a client tick. */
	private static int credits;
	/**
	 * WIN_GAME packets that reached the client: the End Poem they open is replaced by the level loading screen in the same
	 * frame in a plain client (players whose mods skip that screen watch the whole poem), so the screen alone misses it.
	 */
	private static final java.util.concurrent.atomic.AtomicInteger WIN_GAME_PACKETS = new java.util.concurrent.atomic.AtomicInteger();

	/** One armor piece trimmed on the smithing table: the template, the material and the pattern and trim material expected. */
	private record TrimCase(net.minecraft.world.entity.EquipmentSlot slot, String armor, String template, String addition, String pattern, String material) {
	}

	// a method: the static block that schedules the scene runs before the static fields below are set
	private static List<List<TrimCase>> trimSets() {
		return List.of(
			// the mod's patterns with the mod's materials
			List.of(new TrimCase(net.minecraft.world.entity.EquipmentSlot.HEAD, "minecraft:diamond_helmet", "the_deep_void:gore_armor_trim", "the_deep_void:onyx", "the_deep_void:gore", "the_deep_void:onyx_trim"),
					new TrimCase(net.minecraft.world.entity.EquipmentSlot.CHEST, "minecraft:diamond_chestplate", "the_deep_void:scare_armor_trim", "the_deep_void:voidrium_gem", "the_deep_void:scare", "the_deep_void:voidrium_trim"),
					new TrimCase(net.minecraft.world.entity.EquipmentSlot.LEGS, "minecraft:diamond_leggings", "the_deep_void:sorrow_armor_trim", "the_deep_void:bloodstone", "the_deep_void:sorrow", "the_deep_void:bloodstone_trim"),
					new TrimCase(net.minecraft.world.entity.EquipmentSlot.FEET, "minecraft:diamond_boots", "the_deep_void:scavenger_armor_trim", "the_deep_void:cinnabar", "the_deep_void:scavenger", "the_deep_void:cinnabar_trim")),
			// vanilla patterns with the mod's materials, the mod's patterns with vanilla materials
			List.of(new TrimCase(net.minecraft.world.entity.EquipmentSlot.HEAD, "minecraft:diamond_helmet", "minecraft:coast_armor_trim_smithing_template", "the_deep_void:lavendite", "minecraft:coast", "the_deep_void:lavendite_trim"),
					new TrimCase(net.minecraft.world.entity.EquipmentSlot.CHEST, "minecraft:diamond_chestplate", "minecraft:flow_armor_trim_smithing_template", "the_deep_void:soul_shard", "minecraft:flow", "the_deep_void:soul_trim"),
					new TrimCase(net.minecraft.world.entity.EquipmentSlot.LEGS, "minecraft:diamond_leggings", "the_deep_void:gore_armor_trim", "minecraft:gold_ingot", "the_deep_void:gore", "minecraft:gold"),
					new TrimCase(net.minecraft.world.entity.EquipmentSlot.FEET, "minecraft:diamond_boots", "minecraft:bolt_armor_trim_smithing_template", "the_deep_void:sulfur", "minecraft:bolt", "the_deep_void:sulfur_trim")));
	}

	/**
	 * Issue #3: falling into the void and the void pendant must move the player without the End Poem (the MCreator
	 * teleport sent WIN_GAME, which 1.21+ clients always answer with the credits). Issue #2: the mod's trim patterns and
	 * materials on the smithing table, their armor sprites not the missing texture, {@code scene_trims_<n>.png}.
	 */
	private static void regress() {
		at(45, mc -> mc.options.hideGui = true);
		at(50, DevScene::spyPackets);
		at(60, mc -> server(mc, p -> {
			p.teleportTo(p.level().getServer().overworld(), 0.5, -75, 0.5, java.util.Set.of(), 0F, 0F, true);
			net.sombrius.thedeepvoid.procedures.IfPlayerFallsInVoidProcedure.execute(p.getX(), p.getZ(), p);
		}));
		at(200, mc -> travelCheck(mc, "enter (falling into the void)", DEEP_VOID));
		at(210, mc -> server(mc, p -> {
			net.minecraft.world.item.ItemStack pendant = new net.minecraft.world.item.ItemStack(item("void_pendant"));
			p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, pendant);
			net.sombrius.thedeepvoid.procedures.VoidPendantPlayerFinishesUsingItemProcedure.execute(p.level(), p, pendant);
		}));
		at(340, mc -> travelCheck(mc, "leave (void pendant)", Level.OVERWORLD));
		at(350, mc -> server(mc, p -> {
			p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, net.minecraft.world.item.ItemStack.EMPTY);
			p.teleportTo(p.level().getServer().overworld(), 0.5, 120, 0.5, java.util.Set.of(), 0F, 0F, true);
			p.level().setBlock(BlockPos.containing(0, 118, 0), net.minecraft.world.level.block.Blocks.STONE.defaultBlockState(), 3);
			command(p.level().getServer(), "time set 6000");
		}));
		int t = 400;
		List<List<TrimCase>> sets = trimSets();
		for (int i = 0; i < sets.size(); i++) {
			List<TrimCase> set = sets.get(i);
			String name = "trims_" + (i + 1);
			at(t, mc -> server(mc, p -> trimArmor(p, set)));
			at(t + 30, mc -> {
				trimSprites(mc);
				mc.options.setCameraType(net.minecraft.client.CameraType.THIRD_PERSON_FRONT);
			});
			at(t + 60, mc -> shot(mc, name));
			t += 80;
		}
		for (String shield : List.of("lavendite_shield", "eye_shield", "devourer_shield", "voidrium_bulwark")) {
			at(t, mc -> server(mc, p -> holdShield(p, shield)));
			// the client releases an item in use as soon as the use key is up (Minecraft.handleKeybinds)
			at(t + 10, mc -> {
				mc.options.keyUse.setDown(true);
				mc.gameMode.useItem(mc.player, net.minecraft.world.InteractionHand.OFF_HAND);
			});
			at(t + 25, mc -> server(mc, p -> hitShield(p, shield)));
			at(t + 30, mc -> mc.options.keyUse.setDown(false));
			t += 40;
		}
		at(t, mc -> server(mc, p -> {
			p.stopUsingItem();
			p.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND, net.minecraft.world.item.ItemStack.EMPTY);
			p.setGameMode(GameType.CREATIVE);
		}));
		t += 10;
		at(t, mc -> TheDeepVoidMod.LOGGER.info("[scene] CHECK {} End Poem: WIN_GAME packets {}, screen open {} times", WIN_GAME_PACKETS.get() == 0 && credits == 0 ? "OK" : "FAIL",
				WIN_GAME_PACKETS.get(), credits));
		at(t + 10, Minecraft::stop);
	}

	/** Issue #4: the mod's shields raise from the off hand and block like the vanilla one (1.21.5+: the blocks_attacks component). */
	private static void holdShield(ServerPlayer p, String id) {
		p.setGameMode(GameType.SURVIVAL);
		p.stopUsingItem();
		p.teleportTo(p.level().getServer().overworld(), 0.5, 119, 0.5, java.util.Set.of(), 0F, 0F, true);
		p.setHealth(p.getMaxHealth());
		net.minecraft.world.item.ItemStack shield = new net.minecraft.world.item.ItemStack(item(id));
		p.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, net.minecraft.world.item.ItemStack.EMPTY);
		p.setItemInHand(net.minecraft.world.InteractionHand.OFF_HAND, shield);
	}

	/** A zombie in front hits the player; the Lavendite Shield and the Voidrium Bulwark hurt the attacker when they block. */
	private static void hitShield(ServerPlayer p, String id) {
		ServerLevel level = (ServerLevel) p.level();
		boolean raised = p.isUsingItem();
		boolean blocking = p.isBlocking();
		net.minecraft.world.entity.monster.zombie.Zombie zombie = EntityType.ZOMBIE.spawn(level, BlockPos.containing(0.5, 119, 2.5), EntitySpawnReason.COMMAND);
		zombie.setNoAi(true);
		float health = p.getHealth();
		float zombieHealth = zombie.getHealth();
		p.hurtServer(level, p.damageSources().mobAttack(zombie), 4F);
		boolean blocked = p.getHealth() >= health;
		boolean ability = zombie.getHealth() < zombieHealth;
		boolean needAbility = id.equals("lavendite_shield") || id.equals("voidrium_bulwark");
		boolean ok = raised && blocking && blocked && (ability || !needAbility);
		TheDeepVoidMod.LOGGER.info("[scene] CHECK {} shield {}: raised {}, blocking {}, damage taken {}, block ability hit the attacker {}", ok ? "OK" : "FAIL", id, raised, blocking,
				health - p.getHealth(), ability);
		zombie.discard();
	}

	private static void travelCheck(Minecraft mc, String what, ResourceKey<Level> expected) {
		boolean ok = mc.level.dimension() == expected && WIN_GAME_PACKETS.get() == 0 && credits == 0;
		TheDeepVoidMod.LOGGER.info("[scene] CHECK {} travel {}: dimension {}, WIN_GAME packets {}, End Poem screen {} times", ok ? "OK" : "FAIL", what, mc.level.dimension().identifier(),
				WIN_GAME_PACKETS.get(), credits);
	}

	/** Counts the WIN_GAME packets the client receives (a handler ahead of the client's packet listener). */
	private static void spyPackets(Minecraft mc) {
		io.netty.channel.Channel channel;
		try { // Connection.channel() is NeoForge's; the dev run has Mojang's field names
			java.lang.reflect.Field field = net.minecraft.network.Connection.class.getDeclaredField("channel");
			field.setAccessible(true);
			channel = (io.netty.channel.Channel) field.get(mc.getConnection().getConnection());
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
		channel.pipeline().addBefore("packet_handler", "the_deep_void_scene_spy", new io.netty.channel.ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(io.netty.channel.ChannelHandlerContext ctx, Object msg) throws Exception {
				countWinGame(msg);
				super.channelRead(ctx, msg);
			}
		});
	}

	private static void countWinGame(Object packet) {
		if (packet instanceof net.minecraft.network.protocol.game.ClientboundGameEventPacket event && event.getEvent() == net.minecraft.network.protocol.game.ClientboundGameEventPacket.WIN_GAME) {
			TheDeepVoidMod.LOGGER.info("[scene] WIN_GAME packet received");
			WIN_GAME_PACKETS.incrementAndGet();
		} else if (packet instanceof net.minecraft.network.protocol.game.ClientboundBundlePacket bundle) {
			bundle.subPackets().forEach(DevScene::countWinGame);
		}
	}

	private static void trimArmor(ServerPlayer p, List<TrimCase> set) {
		ServerLevel level = p.level();
		for (TrimCase c : set) {
			net.minecraft.world.item.ItemStack base = new net.minecraft.world.item.ItemStack(BuiltInRegistries.ITEM.getValue(Identifier.parse(c.armor())));
			net.minecraft.world.item.crafting.SmithingRecipeInput input = new net.minecraft.world.item.crafting.SmithingRecipeInput(
					new net.minecraft.world.item.ItemStack(BuiltInRegistries.ITEM.getValue(Identifier.parse(c.template()))), base,
					new net.minecraft.world.item.ItemStack(BuiltInRegistries.ITEM.getValue(Identifier.parse(c.addition()))));
			net.minecraft.world.item.ItemStack out = level.getServer().getRecipeManager().getRecipeFor(net.minecraft.world.item.crafting.RecipeType.SMITHING, input, level)
					.map(h -> h.value().assemble(input)).orElse(net.minecraft.world.item.ItemStack.EMPTY);
			net.minecraft.world.item.equipment.trim.ArmorTrim trim = out.get(net.minecraft.core.component.DataComponents.TRIM);
			boolean ok = trim != null && trim.pattern().is(Identifier.parse(c.pattern())) && trim.material().is(Identifier.parse(c.material()));
			TheDeepVoidMod.LOGGER.info("[scene] CHECK {} smithing {} + {} + {} -> {}", ok ? "OK" : "FAIL", c.template(), c.armor(), c.addition(),
					trim == null ? "no trim" : trim.pattern().unwrapKey().map(k -> k.identifier().toString()).orElse("?") + " / " + trim.material().unwrapKey().map(k -> k.identifier().toString()).orElse("?"));
			p.setItemSlot(c.slot(), out.isEmpty() ? base : out);
		}
	}

	/** The armor trim sprites the client draws for the worn armor: none may be the missing texture. */
	private static void trimSprites(Minecraft mc) {
		net.minecraft.client.renderer.texture.TextureAtlas atlas = mc.getAtlasManager().getAtlasOrThrow(net.minecraft.data.AtlasIds.ARMOR_TRIMS);
		for (net.minecraft.world.entity.EquipmentSlot slot : List.of(net.minecraft.world.entity.EquipmentSlot.HEAD, net.minecraft.world.entity.EquipmentSlot.CHEST,
				net.minecraft.world.entity.EquipmentSlot.LEGS, net.minecraft.world.entity.EquipmentSlot.FEET)) {
			net.minecraft.world.item.ItemStack stack = mc.player.getItemBySlot(slot);
			net.minecraft.world.item.equipment.trim.ArmorTrim trim = stack.get(net.minecraft.core.component.DataComponents.TRIM);
			net.minecraft.world.item.equipment.Equippable equippable = stack.get(net.minecraft.core.component.DataComponents.EQUIPPABLE);
			if (trim == null || equippable == null || equippable.assetId().isEmpty()) {
				TheDeepVoidMod.LOGGER.info("[scene] CHECK FAIL trim sprite: {} has no trim", stack);
				continue;
			}
			net.minecraft.client.resources.model.EquipmentClientInfo.LayerType layer = slot == net.minecraft.world.entity.EquipmentSlot.LEGS
					? net.minecraft.client.resources.model.EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS
					: net.minecraft.client.resources.model.EquipmentClientInfo.LayerType.HUMANOID;
			Identifier id = trim.layerAssetId(layer.trimAssetPrefix(), equippable.assetId().get());
			boolean missing = atlas.getSprite(id).contents().name().equals(net.minecraft.client.renderer.texture.MissingTextureAtlasSprite.getLocation());
			TheDeepVoidMod.LOGGER.info("[scene] CHECK {} trim sprite {}", missing ? "FAIL" : "OK", id);
		}
	}

	private static net.minecraft.world.item.Item item(String id) {
		return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, id));
	}

	/** 26.1: the time of day is a clock; the time command sets it */
	private static void command(MinecraftServer server, String command) {
		server.getCommands().performPrefixedCommand(server.createCommandSourceStack().withSuppressedOutput(), command);
	}

	/**
	 * Presses and releases every key of the mod as the game does (KeyMapping.setDown): each one sends its packet to the
	 * server (1.99.3.1.1 crashed on the first press: the key packets were never registered)
	 */
	private static void pressKeys(Minecraft mc) {
		List<String> failed = new ArrayList<>();
		int pressed = 0;
		for (java.lang.reflect.Field field : net.sombrius.thedeepvoid.init.TheDeepVoidModKeyMappings.class.getFields()) {
			if (field.getType() != net.minecraft.client.KeyMapping.class || !java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			try {
				net.minecraft.client.KeyMapping key = (net.minecraft.client.KeyMapping) field.get(null);
				key.setDown(true);
				key.setDown(false);
				pressed++;
			} catch (Throwable e) {
				failed.add(field.getName() + ": " + e);
				TheDeepVoidMod.LOGGER.error("[scene] key {}", field.getName(), e);
			}
		}
		TheDeepVoidMod.LOGGER.info("[scene] CHECK {} keys pressed {}, failed {}", failed.isEmpty() && pressed > 0 ? "OK" : "FAIL", pressed, failed);
	}

	private static void at(int t, Consumer<Minecraft> action) {
		STEPS.add(Pair.of(t, action));
	}

	@SubscribeEvent
	public static void onTick(ClientTickEvent.Post event) {
		if (!ENABLED)
			return;
		Minecraft mc = Minecraft.getInstance();
		if (!opened && ++menuTicks % 100 == 0)
			TheDeepVoidMod.LOGGER.info("[scene] waiting on {}", mc.screen == null ? "no screen" : mc.screen.getClass().getName());
		// a fresh run folder opens the accessibility onboarding first
		if (mc.screen instanceof AccessibilityOnboardingScreen) {
			mc.options.onboardAccessibility = false;
			mc.options.save();
			mc.setScreen(new TitleScreen());
			return;
		}
		if (!opened && mc.screen instanceof TitleScreen) {
			opened = true;
			if (mc.getLevelSource().levelExists(WORLD)) {
				mc.createWorldOpenFlows().openWorld(WORLD, () -> mc.setScreen(new TitleScreen()));
			} else {
				LevelSettings settings = new LevelSettings(WORLD, GameType.CREATIVE, new LevelSettings.DifficultySettings(Difficulty.NORMAL, false, false), true, WorldDataConfiguration.DEFAULT);
				mc.createWorldOpenFlows().createFreshLevel(WORLD, settings, new WorldOptions(1234L, true, false), WorldPresets::createNormalWorldDimensions, new TitleScreen());
			}
			return;
		}
		if (mc.player == null || mc.getSingleplayerServer() == null)
			return;
		mc.options.pauseOnLostFocus = false;
		if (mc.screen instanceof PauseScreen)
			mc.setScreen(null);
		if (mc.screen instanceof net.minecraft.client.gui.screens.WinScreen) {
			credits++;
			TheDeepVoidMod.LOGGER.info("[scene] the End Poem opened at tick {}", tick);
			mc.setScreen(null);
		}
		tick++;
		for (Pair<Integer, Consumer<Minecraft>> step : STEPS) {
			if (step.getFirst() == tick) {
				TheDeepVoidMod.LOGGER.info("[scene] tick {}", tick);
				step.getSecond().accept(mc);
			}
		}
	}

	private static void server(Minecraft mc, Consumer<ServerPlayer> action) {
		MinecraftServer server = mc.getSingleplayerServer();
		server.execute(() -> action.accept(server.getPlayerList().getPlayer(mc.player.getUUID())));
	}

	private static void go(ServerPlayer player, String biome) {
		ServerLevel level = player.level().getServer().getLevel(DEEP_VOID);
		ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, biome));
		Pair<BlockPos, Holder<Biome>> found = level.findClosestBiome3d(h -> h.is(key), new BlockPos(0, 64, 0), 6400, 32, 64);
		BlockPos pos = found == null ? new BlockPos(0, 100, 0) : found.getFirst();
		TheDeepVoidMod.LOGGER.info("[scene] {} at {}", biome, found == null ? "nowhere" : pos);
		player.teleportTo(level, pos.getX() + 0.5, 200, pos.getZ() + 0.5, java.util.Set.of(), 45.0F, 20.0F, true);
	}

	/** Drops the player a few blocks above the ground under them once the chunks are there. */
	private static void settle(ServerPlayer player) {
		ServerLevel level = player.level();
		level.getChunk(player.getBlockX() >> 4, player.getBlockZ() >> 4); // make sure the column is generated
		int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, player.getBlockX(), player.getBlockZ());
		if (y <= level.getMinY() + 1)
			y = 100;
		player.teleportTo(level, player.getX(), y + 8, player.getZ(), java.util.Set.of(), 45.0F, 25.0F, true);
	}

	private static void mobs(ServerPlayer player) {
		ServerLevel level = player.level();
		int i = 0;
		for (String id : List.of("bone_crawler", "stalker", "wanderer", "abductor", "rotten_corpse", "goo_spitter")) {
			EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, id));
			double angle = Math.toRadians(player.getYRot() + 90 - 50 + i * 20);
			BlockPos pos = BlockPos.containing(player.getX() + Math.cos(angle) * 7, player.getY(), player.getZ() + Math.sin(angle) * 7);
			Entity e = type.spawn(level, pos, EntitySpawnReason.COMMAND);
			if (e instanceof Mob mob)
				mob.setNoAi(true);
			i++;
		}
		player.teleportTo(level, player.getX(), player.getY(), player.getZ(), java.util.Set.of(), player.getYRot(), 10.0F, true);
	}

	private static void mobs2(ServerPlayer player) {
		spawnRow(player, List.of("hollowed", "maggot", "eyekin", "wanderer", "lurker", "seeker"));
	}

	private static void spawnRow(ServerPlayer player, List<String> ids) {
		ServerLevel level = player.level();
		int i = 0;
		for (String id : ids) {
			EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, id));
			double angle = Math.toRadians(player.getYRot() + 90 - 50 + i * 20);
			double x = player.getX() + Math.cos(angle) * 7, z = player.getZ() + Math.sin(angle) * 7;
			BlockPos pos = BlockPos.containing(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) x, (int) z), z);
			Entity e = type.spawn(level, pos, EntitySpawnReason.COMMAND);
			if (e instanceof Mob mob)
				mob.setNoAi(true);
			i++;
		}
	}

	private static void shot(Minecraft mc, String name) {
		Screenshot.grab(mc.gameDirectory, "scene_" + name + ".png", mc.getMainRenderTarget(), 1, c -> TheDeepVoidMod.LOGGER.info("[scene] {}", c.getString()));
	}
}
