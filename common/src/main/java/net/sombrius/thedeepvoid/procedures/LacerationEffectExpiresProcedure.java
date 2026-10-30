package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class LacerationEffectExpiresProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:laceration", 0);
	}
}
