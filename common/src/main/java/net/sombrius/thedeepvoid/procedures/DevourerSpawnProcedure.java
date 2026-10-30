package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class DevourerSpawnProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z) {
		if (world.getBiome(BlockPos.containing(x, y, z)).is(Identifier.parse("the_deep_void:gloomy_deathgrounds")) && ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.FLESH_BLOCK.get()
				|| (world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == TheDeepVoidModBlocks.PUSTULENT_FLESH_BLOCK.get() || (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == TheDeepVoidModBlocks.FLESH_BLOCK.get()
				|| (world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == TheDeepVoidModBlocks.PUSTULENT_FLESH_BLOCK.get())) {
			return true;
		}
		return false;
	}
}
