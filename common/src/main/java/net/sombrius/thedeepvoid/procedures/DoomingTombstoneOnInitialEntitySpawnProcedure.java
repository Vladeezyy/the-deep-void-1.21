package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.sombrius.thedeepvoid.entity.DoomingTombstoneEntity;

public class DoomingTombstoneOnInitialEntitySpawnProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof DoomingTombstoneEntity) {
			((DoomingTombstoneEntity) entity).setAnimation("animation.tombstone_fall");
		}
	}
}
