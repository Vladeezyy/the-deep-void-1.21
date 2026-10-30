package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.entity.DarkSteelSawEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class DarkSteelSpikeTrapWalkOnBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof DarkSteelSawEntity) && (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR) {
			world.setBlock(BlockPos.containing(x, y + 1, z), TheDeepVoidModBlocks.TRAP_DARK_STEEL_SPIKES.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:stab")), SoundSource.BLOCKS, 1, (float) 1.4);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:stab")), SoundSource.BLOCKS, 1, (float) 1.4, false);
				}
			}
		}
	}
}
