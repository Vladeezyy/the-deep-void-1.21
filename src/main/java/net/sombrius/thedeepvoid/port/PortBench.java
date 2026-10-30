package net.sombrius.thedeepvoid.port;

import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;

import net.minecraft.world.entity.player.Player;

import net.sombrius.thedeepvoid.TheDeepVoidMod;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;

/**
 * Tick cost measurements of the bench scene ({@code -Pscene=bench}, {@link DevScene}). Times the listeners of the tick
 * events the mod listens to (from the first listener to the last, so other mods are included in both modes), the
 * server tick and the frames; {@link #setModTickHandlers} takes the mod's tick subscribers off the event bus for the
 * base line. {@link DevScene} registers it only for the bench scene, so it costs nothing otherwise.
 */
public final class PortBench {
	/** Player variable / saved data sync packets sent by the server (counted in their sync methods). */
	public static long syncs;
	private static final String[] TICK_SUBSCRIBERS = {"IfPlayerFallsInVoid", "StalkingStalkerLookedAt", "TheGaolPlaySounds", "BoneWalkerOnBoots", "PlayBossMusic", "PlayerIsGrabbedByApostle",
			"HeartbeatPlays", "StalkerBreaksBlocks", "NecrosedProcedure", "ChainGauntletToolInInventoryTick", "PlayerLooksAtStalking", "PlayerInGloomyDeathgrounds", "HunterCooldownWorldTick",
			"PlayerIsInThePit", "PlayerLooksAtStalker", "StalkingStalkerSpawns", "PlayerLooksAtVoidDweller", "RottenArmorAbilityProcedure", "EyekinCooldownProcedure",
			"PlayerInDarknessNearTheStalker", "PlayerForgetfulFogAppears", "SpawnUndertakerInDeepVoid", "PlayerTickUpdateMask", "PlayerIsHallucinating", "CallOfTheVoidDig", "PlayerIsInDeepVoid",
			"AmbientSound", "PlayerHasModifiedItemInHand", "CallOutCooldown", "VoidVisionSpyglassUsed"};
	private static boolean modHandlers = true;
	/** The mod's player tick handlers, called one by one from {@link #playerTickLast} while profiling (they are off the bus then). */
	private static final List<String> PLAYER_HANDLERS = new ArrayList<>();
	private static final List<MethodHandle> PLAYER_HANDLES = new ArrayList<>();
	private static long[] serverHandlerNanos, clientHandlerNanos;
	private static boolean profiling;
	private static boolean measuring;
	private static long serverTickStart, serverPlayerStart, clientPlayerStart, serverEntityStart, clientEntityStart, levelStart;
	private static long serverTicks, serverTickNanos, serverPlayerNanos, clientPlayerTicks, clientPlayerNanos, serverEntityNanos, clientEntityNanos, levelNanos, frames, wallStart, syncStart, serverPlayerMax;
	private static final List<String> RESULTS = new ArrayList<>();

	private PortBench() {
	}

	static void setModTickHandlers(boolean on) {
		if (on == modHandlers)
			return;
		modHandlers = on;
		for (String name : TICK_SUBSCRIBERS) {
			try {
				Class<?> c = Class.forName("net.sombrius.thedeepvoid.procedures." + name + "Procedure");
				if (on)
					NeoForge.EVENT_BUS.register(c);
				else
					NeoForge.EVENT_BUS.unregister(c);
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	/** The mod's handlers off the bus, its player tick handlers timed one by one. */
	static void setProfiling(boolean on) {
		if (PLAYER_HANDLES.isEmpty()) {
			for (String name : TICK_SUBSCRIBERS) {
				try {
					Class<?> c = Class.forName("net.sombrius.thedeepvoid.procedures." + name + "Procedure");
					PLAYER_HANDLES.add(MethodHandles.publicLookup().findStatic(c, "onPlayerTick", MethodType.methodType(void.class, PlayerTickEvent.Post.class)));
					PLAYER_HANDLERS.add(name);
				} catch (NoSuchMethodException e) {
					// not a player tick handler
				} catch (ReflectiveOperationException e) {
					throw new IllegalStateException(e);
				}
			}
		}
		if (on)
			setModTickHandlers(false);
		profiling = on;
		serverHandlerNanos = new long[PLAYER_HANDLES.size()];
		clientHandlerNanos = new long[PLAYER_HANDLES.size()];
	}

	static void begin() {
		serverTicks = serverTickNanos = serverPlayerNanos = clientPlayerTicks = clientPlayerNanos = serverEntityNanos = clientEntityNanos = levelNanos = frames = serverPlayerMax = 0;
		syncStart = syncs;
		wallStart = System.nanoTime();
		measuring = true;
	}

	static void end(String phase) {
		measuring = false;
		double seconds = (System.nanoTime() - wallStart) / 1e9;
		long ticks = Math.max(serverTicks, 1);
		String line = String.format(Locale.ROOT, "[bench] phase=%s mode=%s mspt=%.3f fps=%.1f player_server_us=%.1f player_server_max_us=%.0f player_client_us=%.1f entity_server_us=%.1f entity_client_us=%.1f level_us=%.1f syncs_per_s=%.1f ticks=%d",
				phase, profiling ? "profile" : modHandlers ? "mod" : "base", serverTickNanos / 1e6 / ticks, frames / seconds, serverPlayerNanos / 1e3 / ticks, serverPlayerMax / 1e3, clientPlayerNanos / 1e3 / Math.max(clientPlayerTicks, 1),
				serverEntityNanos / 1e3 / ticks, clientEntityNanos / 1e3 / Math.max(clientPlayerTicks, 1), levelNanos / 1e3 / ticks, (syncs - syncStart) / seconds, serverTicks);
		RESULTS.add(line);
		TheDeepVoidMod.LOGGER.info(line);
		if (profiling) {
			profileLine(phase, "server", serverHandlerNanos, ticks);
			profileLine(phase, "client", clientHandlerNanos, Math.max(clientPlayerTicks, 1));
		}
	}

	private static void profileLine(String phase, String side, long[] nanos, long ticks) {
		List<Integer> order = new ArrayList<>();
		for (int i = 0; i < nanos.length; i++)
			order.add(i);
		order.sort((a, b) -> Long.compare(nanos[b], nanos[a]));
		StringBuilder line = new StringBuilder("[bench] profile phase=" + phase + " side=" + side);
		for (int i : order.subList(0, Math.min(10, order.size())))
			line.append(String.format(Locale.ROOT, " %s=%.1f", PLAYER_HANDLERS.get(i), nanos[i] / 1e3 / ticks));
		TheDeepVoidMod.LOGGER.info(line.toString());
	}

	static void summary() {
		TheDeepVoidMod.LOGGER.info("[bench] done, {} windows", RESULTS.size());
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void serverTickPre(ServerTickEvent.Pre event) {
		serverTickStart = System.nanoTime();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void serverTickPost(ServerTickEvent.Post event) {
		if (measuring) {
			serverTicks++;
			serverTickNanos += System.nanoTime() - serverTickStart;
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void playerTickFirst(PlayerTickEvent.Post event) {
		if (event.getEntity().level().isClientSide())
			clientPlayerStart = System.nanoTime();
		else
			serverPlayerStart = System.nanoTime();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void playerTickLast(PlayerTickEvent.Post event) {
		if (!measuring)
			return;
		if (profiling) {
			long[] acc = event.getEntity().level().isClientSide() ? clientHandlerNanos : serverHandlerNanos;
			for (int i = 0; i < PLAYER_HANDLES.size(); i++) {
				long t = System.nanoTime();
				try {
					PLAYER_HANDLES.get(i).invokeExact(event);
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
				acc[i] += System.nanoTime() - t;
			}
		}
		if (event.getEntity().level().isClientSide()) {
			clientPlayerTicks++;
			clientPlayerNanos += System.nanoTime() - clientPlayerStart;
		} else {
			long t = System.nanoTime() - serverPlayerStart;
			serverPlayerNanos += t;
			serverPlayerMax = Math.max(serverPlayerMax, t);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void entityTickFirst(EntityTickEvent.Pre event) {
		if (event.getEntity() instanceof Player)
			return;
		if (event.getEntity().level().isClientSide())
			clientEntityStart = System.nanoTime();
		else
			serverEntityStart = System.nanoTime();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void entityTickLast(EntityTickEvent.Pre event) {
		if (!measuring || event.getEntity() instanceof Player)
			return;
		if (event.getEntity().level().isClientSide())
			clientEntityNanos += System.nanoTime() - clientEntityStart;
		else
			serverEntityNanos += System.nanoTime() - serverEntityStart;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void levelTickFirst(LevelTickEvent.Post event) {
		if (!event.getLevel().isClientSide())
			levelStart = System.nanoTime();
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void levelTickLast(LevelTickEvent.Post event) {
		if (measuring && !event.getLevel().isClientSide())
			levelNanos += System.nanoTime() - levelStart;
	}

	@SubscribeEvent
	public static void frame(RenderFrameEvent.Post event) {
		if (measuring)
			frames++;
	}
}
