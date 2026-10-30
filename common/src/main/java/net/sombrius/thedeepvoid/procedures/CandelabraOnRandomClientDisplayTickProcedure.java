package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class CandelabraOnRandomClientDisplayTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, ResourceLocation.parse("the_deep_void:candelabra")))) {
			if (Math.random() < 0.2) {
				for (int index0 = 0; index0 < Mth.nextInt(RandomSource.create(), 0, 6); index0++) {
					world.addParticle(ParticleTypes.FLAME, (x + Mth.nextDouble(RandomSource.create(), 0.1, 0.9)), (y + Mth.nextDouble(RandomSource.create(), 1, 1.1)), (z + Mth.nextDouble(RandomSource.create(), 0.1, 0.9)), 0, 0, 0);
				}
			}
		} else if ((world.getBlockState(BlockPos.containing(x, y, z))).is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, ResourceLocation.parse("the_deep_void:candlestick")))) {
			if (Math.random() < 0.2) {
				world.addParticle(ParticleTypes.FLAME, (x + 0.5), (y + 0.9), (z + 0.5), 0, 0, 0);
			}
		}
		if (Math.random() < 0.05) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.candle.ambient")), SoundSource.BLOCKS, (float) Mth.nextDouble(RandomSource.create(), 0.7, 2),
							(float) Mth.nextDouble(RandomSource.create(), 0.3, 1));
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.candle.ambient")), SoundSource.BLOCKS, (float) Mth.nextDouble(RandomSource.create(), 0.7, 2),
							(float) Mth.nextDouble(RandomSource.create(), 0.3, 1), false);
				}
			}
		}
	}
}
