package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class LacerationStopBleedingProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDoubleOr("deep_void:laceration", 0) > 0) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("deep_void:laceration", 0);
			}
		}
	}
}
