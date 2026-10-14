package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModParticleTypes;

public class ShadeArrowWhileProjectileFlyingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (TheDeepVoidModParticleTypes.SHADE_DROP.get()), x, y, z, 5, 0.1, 0.1, 0.1, 0.1);
	}
}
