package net.sombrius.thedeepvoid.port;

import net.sombrius.thedeepvoid.port.bus.ServerTickEvent;
import net.sombrius.thedeepvoid.port.bus.ModBus;
import net.sombrius.thedeepvoid.port.bus.EventPriority;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.init.TheDeepVoidModGameRules;
import net.sombrius.thedeepvoid.configuration.DeepVoidConfigConfiguration;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Field;

/**
 * Dedicated server self-test, inert unless the {@code deepvoid.servertest} system property is set
 * ({@code tools/servertest.sh}, {@code -Pservertest=fresh|restart}). On the first server tick it checks the structures
 * {@code CultTemplePlacementProcedure} places once per world when the levels load (the cult temple in the overworld, the
 * Weaver's sepulcher in the Deep Void, the pit in the Pit): every non-air block of the template has to be in the world,
 * and the map variables have to remember them. {@code fresh} (a new world) then swaps one block of each structure for
 * glass; {@code restart} (the same world started again) expects the glass, so the structures were not placed a second
 * time. Logs {@code [servertest] PASS} or {@code [servertest] FAIL} and stops the server (the world is saved).
 */
public final class PortServerTest {
	public static final String MODE = System.getProperty("deepvoid.servertest");
	private static final BlockState MARK = Blocks.GLASS.defaultBlockState();
	private static boolean done;

	private PortServerTest() {
	}

	/** Called from {@link TheDeepVoidMod#init()}: no listener at all unless the property is set */
	public static void register() {
		if (MODE != null)
			ModBus.add(ServerTickEvent.Post.class, EventPriority.NORMAL, PortServerTest::tick);
	}

	private static void tick(ServerTickEvent.Post event) {
		if (done)
			return;
		done = true;
		MinecraftServer server = event.getServer();
		boolean restart = "restart".equals(MODE);
		List<String> failures = new ArrayList<>();
		if (DeepVoidConfigConfiguration.PLACECULTTEMPLE.get() && PortUtil.rule(server.overworld(), TheDeepVoidModGameRules.SPAWN_HOLE_STRUCTURE.get())) {
			int y = (int) Math.floor(DeepVoidConfigConfiguration.HOLESTRUCTUREY.get());
			if (DeepVoidConfigConfiguration.OLDHOLESTRUCTURE.get())
				check(server, Level.OVERWORLD, "void_cult_temple", new BlockPos(-8, y, -8), restart, failures);
			else
				check(server, Level.OVERWORLD, "cult_temple_noair3", new BlockPos(-12, y, -12), restart, failures);
		} else {
			TheDeepVoidMod.LOGGER.info("[servertest] cult temple: off in the config or the game rule");
		}
		check(server, dimension("deep_void"), "weaver_sepulcher_nogates", new BlockPos(-40, 1, -40), restart, failures);
		check(server, dimension("the_pit"), "the_pit_7", new BlockPos(0, 0, 0), restart, failures);
		TheDeepVoidModVariables.MapVariables vars = TheDeepVoidModVariables.MapVariables.get(server.overworld());
		TheDeepVoidMod.LOGGER.info("[servertest] map variables: CultTemplePlaced={} WeaverTemplePlaced={} pitPlaced={}", vars.CultTemplePlaced, vars.WeaverTemplePlaced, vars.pitPlaced);
		if (!vars.WeaverTemplePlaced || !vars.pitPlaced || !vars.CultTemplePlaced && DeepVoidConfigConfiguration.PLACECULTTEMPLE.get())
			failures.add("map variables");
		if (failures.isEmpty())
			TheDeepVoidMod.LOGGER.info("[servertest] PASS ({})", MODE);
		else
			TheDeepVoidMod.LOGGER.error("[servertest] FAIL ({}): {}", MODE, String.join(", ", failures));
		server.halt(false);
	}

	private static ResourceKey<Level> dimension(String name) {
		return ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, name));
	}

	/** Compares the template's non-air blocks (block type, rotation NONE, as the procedure places it) with the world */
	private static void check(MinecraftServer server, ResourceKey<Level> dimension, String name, BlockPos origin, boolean restart, List<String> failures) {
		ServerLevel level = server.getLevel(dimension);
		if (level == null) {
			failures.add(name + ": no level " + dimension.identifier());
			return;
		}
		StructureTemplate template = level.getStructureManager().getOrCreate(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, name));
		List<StructureTemplate.StructureBlockInfo> blocks = blocks(template);
		int total = 0, found = 0;
		BlockPos mark = null;
		for (StructureTemplate.StructureBlockInfo info : blocks) {
			if (info.state().isAir())
				continue;
			BlockPos pos = origin.offset(info.pos());
			if (mark == null && !info.state().is(MARK.getBlock())) {
				mark = pos;
				if (restart)
					continue; // the glass of the first run
			}
			total++;
			if (level.getBlockState(pos).is(info.state().getBlock()))
				found++;
		}
		boolean placed = total > 0 && found >= total * 0.95;
		String marked = "";
		if (mark != null && restart) {
			boolean kept = level.getBlockState(mark).is(MARK.getBlock());
			marked = kept ? ", the glass of the first run is there (not placed again)" : ", the glass of the first run is gone (placed again)";
			if (!kept)
				failures.add(name + " placed again");
		} else if (mark != null && placed) {
			level.setBlock(mark, MARK, 3);
		}
		TheDeepVoidMod.LOGGER.info("[servertest] {} in {} at {} {} {}: {} of {} blocks{}", name, dimension.identifier(), origin.getX(), origin.getY(), origin.getZ(), found, total, marked);
		if (!placed)
			failures.add(name + " missing (" + found + " of " + total + ")");
	}

	/** The template's blocks: its palettes are private, and the list is the only field of type List<Palette> */
	@SuppressWarnings("unchecked")
	private static List<StructureTemplate.StructureBlockInfo> blocks(StructureTemplate template) {
		try {
			for (Field field : StructureTemplate.class.getDeclaredFields()) {
				if (field.getGenericType() instanceof ParameterizedType type && type.getActualTypeArguments()[0] == StructureTemplate.Palette.class) {
					field.setAccessible(true);
					List<StructureTemplate.Palette> palettes = (List<StructureTemplate.Palette>) field.get(template);
					return palettes.isEmpty() ? List.of() : palettes.get(0).blocks();
				}
			}
		} catch (ReflectiveOperationException e) {
			TheDeepVoidMod.LOGGER.error("[servertest] template palettes", e);
		}
		return List.of();
	}
}
