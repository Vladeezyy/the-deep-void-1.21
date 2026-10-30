package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class PrisonGuardWanderConditionProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("disableWander", false) == false) {
			return true;
		}
		return false;
	}
}
