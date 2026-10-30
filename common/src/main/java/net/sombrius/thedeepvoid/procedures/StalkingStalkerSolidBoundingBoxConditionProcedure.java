package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class StalkingStalkerSolidBoundingBoxConditionProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("deep_void:crouching") == true) {
			return 0.5;
		}
		return 1;
	}
}
