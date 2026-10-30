package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class FlyWingsChestplateTouchGroundProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("flyWingsTouchGround", false) == false && entity.onGround()) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("flyWingsTouchGround", true);
		}
	}
}
