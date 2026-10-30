package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WatcherOnEmittedRedstonePowerProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		if ((new Object() {
			public boolean getValue(LevelAccessor world, BlockPos pos, String tag) {
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity != null)
					return net.sombrius.thedeepvoid.port.PortUtil.data(blockEntity).getBooleanOr(tag, false);
				return false;
			}
		}.getValue(world, BlockPos.containing(x, y, z), "sees")) == true) {
			return 10;
		}
		return 0;
	}
}
