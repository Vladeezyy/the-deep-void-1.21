package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class SulfurOreBlockValidPlacementConditionProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (!((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == Blocks.LAVA) && !((world.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == Blocks.LAVA)
				&& !((world.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == Blocks.LAVA) && !((world.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == Blocks.LAVA)
				&& !((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.LAVA) && !((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.LAVA)) {
			return true;
		}
		return false;
	}
}
