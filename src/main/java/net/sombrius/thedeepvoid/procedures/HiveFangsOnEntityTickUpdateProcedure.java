package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

public class HiveFangsOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		TheDeepVoidMod.queueServerWork(20, () -> {
			if (!entity.level().isClientSide())
				entity.discard();
		});
	}
}
