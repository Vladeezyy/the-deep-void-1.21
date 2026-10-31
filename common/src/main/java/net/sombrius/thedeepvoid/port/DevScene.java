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
 * tick handlers ({@link PortBench}, {@code tools/bench.sh}).
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
	private static final String WORLD = LANG_ONLY ? "dv_lang" : BENCH ? "dv_bench" : ITEMS ? "dv_items" : "dv_scene";
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
			level.getServer().overworld().setDayTime(6000);
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
			p.level().getServer().overworld().setDayTime(6000);
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

	private static net.minecraft.world.item.Item item(String id) {
		return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, id));
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
				LevelSettings settings = new LevelSettings(WORLD, GameType.CREATIVE, false, Difficulty.NORMAL, true, new GameRules(WorldDataConfiguration.DEFAULT.enabledFeatures()), WorldDataConfiguration.DEFAULT);
				mc.createWorldOpenFlows().createFreshLevel(WORLD, settings, new WorldOptions(1234L, true, false), WorldPresets::createNormalWorldDimensions, new TitleScreen());
			}
			return;
		}
		if (mc.player == null || mc.getSingleplayerServer() == null)
			return;
		mc.options.pauseOnLostFocus = false;
		if (mc.screen instanceof PauseScreen)
			mc.setScreen(null);
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
