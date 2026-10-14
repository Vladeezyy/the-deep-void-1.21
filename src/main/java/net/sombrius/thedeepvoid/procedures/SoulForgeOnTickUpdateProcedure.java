package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class SoulForgeOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.SCULK_SOUL, x, (y + 0.8), z, 2, 0, 0.1, 0, 0.1);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y + 0.8, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
			} else {
				_level.playLocalSound(x, (y + 0.8), z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.BLOCKS, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
			}
		}
	}
}
