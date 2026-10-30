package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class HiveWatcherMoveRandomlyProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		double rnd = 0;
		net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("speedBuildUp", 0);
		rnd = Mth.nextInt(RandomSource.create(), 1, 2);
		if (rnd == 1) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("moveInX", true);
			if (Math.random() < 0.5) {
				if (world.getBlockState(BlockPos.containing(entity.getX() - 1, entity.getY(), entity.getZ())).canOcclude() || world.getBlockState(BlockPos.containing(entity.getX() - 2, entity.getY(), entity.getZ())).canOcclude()
						|| world.getBlockState(BlockPos.containing(entity.getX() - 3, entity.getY(), entity.getZ())).canOcclude()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", true);
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", false);
				}
			} else {
				if (world.getBlockState(BlockPos.containing(entity.getX() + 1, entity.getY(), entity.getZ())).canOcclude() || world.getBlockState(BlockPos.containing(entity.getX() + 2, entity.getY(), entity.getZ())).canOcclude()
						|| world.getBlockState(BlockPos.containing(entity.getX() + 3, entity.getY(), entity.getZ())).canOcclude()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", false);
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", true);
				}
			}
		} else if (rnd == 2) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("moveInZ", true);
			if (Math.random() < 0.5) {
				if (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 1)).canOcclude() || world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 2)).canOcclude()
						|| world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() - 3)).canOcclude()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", true);
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", false);
				}
			} else {
				if (world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 1)).canOcclude() || world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 2)).canOcclude()
						|| world.getBlockState(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ() + 3)).canOcclude()) {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", false);
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("positive", true);
				}
			}
		}
	}
}
