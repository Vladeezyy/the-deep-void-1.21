package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class PainfulEffectExpiresProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("deep_void:advancedPainful", false) == true) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("deep_void:advancedPainful", false);
		}
	}
}
