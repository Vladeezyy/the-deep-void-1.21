package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.entity.PrisonGuardEntity;

public class PrisonGuardEntityDiesProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof PrisonGuardEntity) {
			((PrisonGuardEntity) entity).setAnimation("empty");
		}
		if (entity instanceof PrisonGuardEntity) {
			((PrisonGuardEntity) entity).setAnimation("animation.prisonGuard_death");
		}
	}
}
