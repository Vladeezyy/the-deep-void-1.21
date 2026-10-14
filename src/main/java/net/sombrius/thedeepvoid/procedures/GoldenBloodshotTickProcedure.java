package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;

public class GoldenBloodshotTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		immediatesourceentity.setNoGravity(true);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.GOLD_BLOOD.get()), x, y, z, 1, 0.01, (-0.01), 0.01, 0.1);
		TheDeepVoidMod.queueServerWork(15, () -> {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		});
	}
}
