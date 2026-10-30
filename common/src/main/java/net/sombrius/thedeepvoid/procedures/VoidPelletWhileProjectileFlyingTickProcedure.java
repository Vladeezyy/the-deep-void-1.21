package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;

public class VoidPelletWhileProjectileFlyingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		immediatesourceentity.setNoGravity(true);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.DARK_TEAR.get()), x, y, z, 2, 0.01, (-0.01), 0.01, 0.1);
		if (net.sombrius.thedeepvoid.port.PortUtil.data(immediatesourceentity).getBooleanOr("shot", false) == false) {
			net.sombrius.thedeepvoid.port.PortUtil.data(immediatesourceentity).putBoolean("shot", true);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.SQUID_INK, x, y, z, 5, 0.1, (-0.1), 0.1, 0.1);
		}
		TheDeepVoidMod.queueServerWork(15, () -> {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		});
	}
}
