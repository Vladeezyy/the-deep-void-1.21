package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;
import org.joml.Vector3f;

public class ManiacShotgunPelletTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		world.addParticle((new DustParticleOptions(new Vector3f(76 / 255.0F, 76 / 255.0F, 76 / 255.0F), 1)), x, y, z, 0, 0, 0);
		if (!immediatesourceentity.isNoGravity()) {
			immediatesourceentity.setNoGravity(true);
		}
		TheDeepVoidMod.queueServerWork(8, () -> {
			if (!immediatesourceentity.level().isClientSide())
				immediatesourceentity.discard();
		});
	}
}
