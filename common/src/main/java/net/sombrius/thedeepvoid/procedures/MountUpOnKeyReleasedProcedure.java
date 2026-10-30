package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.entity.RideableFlyingEyekinEntity;

public class MountUpOnKeyReleasedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity.getVehicle()) instanceof RideableFlyingEyekinEntity) {
			net.sombrius.thedeepvoid.port.PortUtil.data((entity.getVehicle())).putDouble("verticalMovement", 0);
		}
	}
}
