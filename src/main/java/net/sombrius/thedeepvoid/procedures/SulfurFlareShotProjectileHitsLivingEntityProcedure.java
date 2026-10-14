package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class SulfurFlareShotProjectileHitsLivingEntityProcedure {
	public static void execute(LevelAccessor world, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		if (!immediatesourceentity.level().isClientSide())
			immediatesourceentity.discard();
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, (immediatesourceentity.getX()), (immediatesourceentity.getY()), (immediatesourceentity.getZ()), 2, Level.ExplosionInteraction.NONE);
	}
}
