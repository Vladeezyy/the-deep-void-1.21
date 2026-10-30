package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class MaggotEntityVisualScaleProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) >= 6 && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) < 14) {
			return 1.25;
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) >= 14 && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) < 28) {
			return 1.5;
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) >= 28) {
			return 1.75;
		}
		return 1;
	}
}
