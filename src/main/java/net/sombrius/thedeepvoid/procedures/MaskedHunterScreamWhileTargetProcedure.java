package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.MaskedHunterEntity;

public class MaskedHunterScreamWhileTargetProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) instanceof Player) {
			if ((entity instanceof MaskedHunterEntity _datEntI ? _datEntI.getEntityData().get(MaskedHunterEntity.DATA_playSound) : 0) <= 0) {
				if (entity instanceof MaskedHunterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MaskedHunterEntity.DATA_playSound, 200);
				if (entity instanceof MaskedHunterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MaskedHunterEntity.DATA_randomPlaySound, Mth.nextInt(RandomSource.create(), 1, 2));
				if ((entity instanceof MaskedHunterEntity _datEntI ? _datEntI.getEntityData().get(MaskedHunterEntity.DATA_randomPlaySound) : 0) == 1) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:masked_hunter_scream1")), SoundSource.HOSTILE, 3,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.1));
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:masked_hunter_scream1")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.1), false);
						}
					}
				} else if ((entity instanceof MaskedHunterEntity _datEntI ? _datEntI.getEntityData().get(MaskedHunterEntity.DATA_randomPlaySound) : 0) == 2) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:masked_hunter_scream2")), SoundSource.HOSTILE, 3,
									(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.1));
						} else {
							_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:masked_hunter_scream2")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.1), false);
						}
					}
				}
			} else {
				if (entity instanceof MaskedHunterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MaskedHunterEntity.DATA_playSound, (int) ((entity instanceof MaskedHunterEntity _datEntI ? _datEntI.getEntityData().get(MaskedHunterEntity.DATA_playSound) : 0) - 1));
			}
		}
	}
}
