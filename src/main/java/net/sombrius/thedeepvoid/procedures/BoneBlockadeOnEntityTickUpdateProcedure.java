package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

public class BoneBlockadeOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		TheDeepVoidMod.queueServerWork(40, () -> {
			if (!entity.level().isClientSide())
				entity.discard();
		});
	}
}
