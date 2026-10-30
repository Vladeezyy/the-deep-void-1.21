package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class LiquidVoidCheckBelowProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.WATER) {
			world.setBlock(BlockPos.containing(x, y - 1, z), TheDeepVoidModBlocks.SOLID_VOID_BLOCK.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y - 1, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.sculk.place")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, (y - 1), z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.sculk.place")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
		}
	}
}
