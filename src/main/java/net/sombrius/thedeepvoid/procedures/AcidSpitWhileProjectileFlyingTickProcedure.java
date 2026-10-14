package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class AcidSpitWhileProjectileFlyingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.ITEM_SLIME, x, y, z, 2, 0.05, 0.05, 0.05, 0.1);
	}
}
