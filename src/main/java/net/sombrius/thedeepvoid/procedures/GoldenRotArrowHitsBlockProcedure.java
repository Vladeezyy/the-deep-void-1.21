package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class GoldenRotArrowHitsBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.arrow.hit")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3));
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("entity.arrow.hit")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3), false);
			}
		}
	}
}
