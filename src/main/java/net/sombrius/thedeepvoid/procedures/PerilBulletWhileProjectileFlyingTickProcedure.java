package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;

public class PerilBulletWhileProjectileFlyingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.BLOOD.get()), x, y, z, 1, 0, (-0.01), 0, 0);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.DARK_TEAR.get()), x, y, z, 1, 0, (-0.01), 0, 0);
		TheDeepVoidMod.queueServerWork(20, () -> {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		});
	}
}
