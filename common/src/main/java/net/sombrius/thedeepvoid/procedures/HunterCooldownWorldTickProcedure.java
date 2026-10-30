package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.LevelTickEvent;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

@EventBusSubscriber
public class HunterCooldownWorldTickProcedure {
	@SubscribeEvent
	public static void onWorldTick(LevelTickEvent.Post event) {
		execute(event, event.getLevel());
	}

	public static void execute(LevelAccessor world) {
		execute(null, world);
	}

	private static void execute(@Nullable Event event, LevelAccessor world) {
		if (TheDeepVoidModVariables.MapVariables.get(world).hunterSpawnCooldown > 0) {
			TheDeepVoidModVariables.MapVariables.get(world).hunterSpawnCooldown = TheDeepVoidModVariables.MapVariables.get(world).hunterSpawnCooldown - 1;
			TheDeepVoidModVariables.MapVariables.get(world).setDirty(); // only the server reads it: saved, not sent every tick
		}
	}
}
