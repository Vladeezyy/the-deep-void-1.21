package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

public class BlossomOnEffectActiveTickProcedure {
	public static void execute(LevelAccessor world, Entity entity, double amplifier) {
		if (entity == null)
			return;
		entity.hurt(new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(DamageTypes.MAGIC)), (float) (4 + amplifier));
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.chorus_flower.death")), SoundSource.NEUTRAL, (float) 0.4,
						(float) Mth.nextDouble(RandomSource.create(), 0.8, 1));
			} else {
				_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("block.chorus_flower.death")), SoundSource.NEUTRAL, (float) 0.4,
						(float) Mth.nextDouble(RandomSource.create(), 0.8, 1), false);
			}
		}
	}
}
