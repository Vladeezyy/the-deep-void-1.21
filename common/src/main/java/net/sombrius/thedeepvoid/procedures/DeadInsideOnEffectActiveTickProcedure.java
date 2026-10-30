package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class DeadInsideOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		boolean stop = false;
		if (TheDeepVoidModVariables.of(entity).StalkerCount != 300 && TheDeepVoidModVariables.of(entity).StalkerCountFinish == false && entity instanceof Player) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = TheDeepVoidModVariables.of(entity);
				_vars.StalkerCount = TheDeepVoidModVariables.of(entity).StalkerCount + 2;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (world.getBlockState(BlockPos.containing(x, y, z)).getLightEmission() > 0) {
			world.destroyBlock(BlockPos.containing(x, y, z), false);
		}
	}
}
