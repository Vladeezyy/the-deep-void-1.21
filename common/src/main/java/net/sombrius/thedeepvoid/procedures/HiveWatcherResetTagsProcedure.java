package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class HiveWatcherResetTagsProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("moveInPos", false);
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("stopMove", 0);
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("moveInX", false);
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("moveInZ", false);
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", false);
	}
}
