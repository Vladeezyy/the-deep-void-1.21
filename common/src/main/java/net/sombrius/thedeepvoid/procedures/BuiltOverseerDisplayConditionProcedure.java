package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class BuiltOverseerDisplayConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("deep_void:overseerSaddle", false) == true) {
			return true;
		}
		return false;
	}
}
