package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class ForsakenOnInitialEntitySpawnProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("deep_void:hide", true);
	}
}
