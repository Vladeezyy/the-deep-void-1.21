package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.entity.FleshFangsEntity;

public class FleshFangsOnInitialEntitySpawnProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof FleshFangsEntity) {
			((FleshFangsEntity) entity).setAnimation("animation.fleshFangs_appear");
		}
	}
}
