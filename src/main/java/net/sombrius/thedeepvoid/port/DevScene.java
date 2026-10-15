package net.sombrius.thedeepvoid.port;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.BlockPos;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.Screenshot;
import net.minecraft.client.Minecraft;

import net.sombrius.thedeepvoid.TheDeepVoidMod;

import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;

import com.mojang.datafixers.util.Pair;

/**
 * Development scene, inert unless the {@code deepvoid.scene} system property is set ({@code ./gradlew runScene}): creates or
 * opens the world {@code dv_scene} (seed 1234), visits Deep Void biomes, spawns some of the mod's mobs and saves
 * {@code run/screenshots/scene_*.png}, then quits. With {@code -Pscene=lang} it only prints some of the mod's translated texts
 * to the chat and saves {@code scene_lang_<language>.png}.
 */
@EventBusSubscriber(modid = TheDeepVoidMod.MODID, value = Dist.CLIENT)
public final class DevScene {
	private static final boolean ENABLED = System.getProperty("deepvoid.scene") != null;
	private static final boolean LANG_ONLY = "lang".equals(System.getProperty("deepvoid.scene"));
	private static final List<String> LANG_KEYS = List.of("item.the_deep_void.void_pendant", "item.the_deep_void.void_pendant.descr1", "biome.the_deep_void.misted_remnants",
			"entity.the_deep_void.stalker", "text.the_deep_void.port.the_stalker_is_after_you", "text.the_deep_void.port.press_shift_to_show_abilities", "effect.the_deep_void.hexed.description",
			"entity.the_deep_void.undertaker.canopy1");
	private static final String WORLD = LANG_ONLY ? "dv_lang" : "dv_scene";
	private static final ResourceKey<Level> DEEP_VOID = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("the_deep_void:deep_void"));
	private static final List<Pair<Integer, Consumer<Minecraft>>> STEPS = new ArrayList<>();
	private static boolean opened;
	private static int tick;

	static {
		at(40, mc -> server(mc, p -> {
			p.setGameMode(GameType.CREATIVE);
			p.getAbilities().flying = true;
			p.onUpdateAbilities();
			p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1000000, 0, false, false));
			MinecraftServer server = p.getServer();
			server.getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING).set(false, server);
		}));
		if (LANG_ONLY) {
			at(100, mc -> server(mc, p -> LANG_KEYS.forEach(key -> p.sendSystemMessage(Component.translatable(key)))));
			at(140, mc -> shot(mc, "lang_" + mc.options.languageCode));
			at(160, Minecraft::stop);
		} else {
			biomesAndMobs();
		}
	}

	private static void biomesAndMobs() {
		at(45, mc -> mc.options.hideGui = true);
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

	private static void at(int t, Consumer<Minecraft> action) {
		STEPS.add(Pair.of(t, action));
	}

	@SubscribeEvent
	public static void onTick(ClientTickEvent.Post event) {
		if (!ENABLED)
			return;
		Minecraft mc = Minecraft.getInstance();
		if (!opened && mc.screen instanceof TitleScreen) {
			opened = true;
			if (mc.getLevelSource().levelExists(WORLD)) {
				mc.createWorldOpenFlows().openWorld(WORLD, () -> mc.setScreen(new TitleScreen()));
			} else {
				LevelSettings settings = new LevelSettings(WORLD, GameType.CREATIVE, false, Difficulty.NORMAL, true, new GameRules(), WorldDataConfiguration.DEFAULT);
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
		ServerLevel level = player.getServer().getLevel(DEEP_VOID);
		ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, biome));
		Pair<BlockPos, Holder<Biome>> found = level.findClosestBiome3d(h -> h.is(key), new BlockPos(0, 64, 0), 6400, 32, 64);
		BlockPos pos = found == null ? new BlockPos(0, 100, 0) : found.getFirst();
		TheDeepVoidMod.LOGGER.info("[scene] {} at {}", biome, found == null ? "nowhere" : pos);
		player.teleportTo(level, pos.getX() + 0.5, 200, pos.getZ() + 0.5, 45.0F, 20.0F);
	}

	/** Drops the player a few blocks above the ground under them once the chunks are there. */
	private static void settle(ServerPlayer player) {
		ServerLevel level = player.serverLevel();
		level.getChunk(player.getBlockX() >> 4, player.getBlockZ() >> 4); // make sure the column is generated
		int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING, player.getBlockX(), player.getBlockZ());
		if (y <= level.getMinBuildHeight() + 1)
			y = 100;
		player.teleportTo(level, player.getX(), y + 8, player.getZ(), 45.0F, 25.0F);
	}

	private static void mobs(ServerPlayer player) {
		ServerLevel level = player.serverLevel();
		int i = 0;
		for (String id : List.of("bone_crawler", "stalker", "wanderer", "abductor", "rotten_corpse", "goo_spitter")) {
			EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, id));
			double angle = Math.toRadians(player.getYRot() + 90 - 50 + i * 20);
			BlockPos pos = BlockPos.containing(player.getX() + Math.cos(angle) * 7, player.getY(), player.getZ() + Math.sin(angle) * 7);
			Entity e = type.spawn(level, pos, MobSpawnType.COMMAND);
			if (e instanceof Mob mob)
				mob.setNoAi(true);
			i++;
		}
		player.teleportTo(level, player.getX(), player.getY(), player.getZ(), player.getYRot(), 10.0F);
	}

	private static void mobs2(ServerPlayer player) {
		spawnRow(player, List.of("hollowed", "maggot", "eyekin", "wanderer", "lurker", "seeker"));
	}

	private static void spawnRow(ServerPlayer player, List<String> ids) {
		ServerLevel level = player.serverLevel();
		int i = 0;
		for (String id : ids) {
			EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath(TheDeepVoidMod.MODID, id));
			double angle = Math.toRadians(player.getYRot() + 90 - 50 + i * 20);
			double x = player.getX() + Math.cos(angle) * 7, z = player.getZ() + Math.sin(angle) * 7;
			BlockPos pos = BlockPos.containing(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) x, (int) z), z);
			Entity e = type.spawn(level, pos, MobSpawnType.COMMAND);
			if (e instanceof Mob mob)
				mob.setNoAi(true);
			i++;
		}
	}

	private static void shot(Minecraft mc, String name) {
		Screenshot.grab(mc.gameDirectory, "scene_" + name + ".png", mc.getMainRenderTarget(), c -> TheDeepVoidMod.LOGGER.info("[scene] {}", c.getString()));
	}
}
