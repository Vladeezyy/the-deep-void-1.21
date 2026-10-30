package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class BabyBoneCrawlerNeutralEntityVisualScaleProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) >= 15 && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) < 35) {
			return 1.15;
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) >= 35 && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) < 50) {
			return 1.22;
		} else if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("growth", 0) >= 50) {
			return 1.3;
		}
		return 1;
	}
}
