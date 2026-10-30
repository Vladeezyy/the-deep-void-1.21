package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;

public class BuiltOverseerOnEntityTickUpdateProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.isVehicle()) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("deep_void:overseerSaddle", false) == false) {
				if (!((entity.getFirstPassenger()) == null)) {
					(entity.getFirstPassenger()).stopRiding();
				}
			}
		}
	}
}
