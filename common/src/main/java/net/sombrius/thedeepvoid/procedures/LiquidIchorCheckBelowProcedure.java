package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.sombrius.thedeepvoid.init.TheDeepVoidModBlocks;

public class LiquidIchorCheckBelowProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y - 1, z))).getBlock() == Blocks.WATER) {
			for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 12, 24); index0++) {
				world.addParticle(ParticleTypes.SMOKE, (x + 0.5), y, (z + 0.5), (Mth.nextDouble(RandomSource.create(), -0.05, 0.05)), (Mth.nextDouble(RandomSource.create(), 0.4, 0.6)), (Mth.nextDouble(RandomSource.create(), -0.05, 0.05)));
			}
			world.setBlock(BlockPos.containing(x, y - 1, z), TheDeepVoidModBlocks.CRYSTALLIZED_ICHOR.get().defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y - 1, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.fire.extinguish")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 0.83));
				} else {
					_level.playLocalSound(x, (y - 1), z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.fire.extinguish")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 0.83), false);
				}
			}
		}
	}
}
