package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.LevelAccessor;

public class SulfurBombProjWhileProjectileFlyingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		world.addParticle(ParticleTypes.SMOKE, x, (y + 0.8), z, 0, 0, 0);
	}
}
