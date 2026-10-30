package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;

public class IchorFlareShotWhileProjectileFlyingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.ICHOR_SMOKE.get()), x, y, z, Mth.nextInt(RandomSource.create(), 1, 2), 0, 0.08, 0, 0.1);
		if (net.sombrius.thedeepvoid.port.PortUtil.data(immediatesourceentity).getDoubleOr("despawn", 0) >= 100) {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		} else {
			net.sombrius.thedeepvoid.port.PortUtil.data(immediatesourceentity).putDouble("despawn", (net.sombrius.thedeepvoid.port.PortUtil.data(immediatesourceentity).getDoubleOr("despawn", 0) + 1));
		}
	}
}
