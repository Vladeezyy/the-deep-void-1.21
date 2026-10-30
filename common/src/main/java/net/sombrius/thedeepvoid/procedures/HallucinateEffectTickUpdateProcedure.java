package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class HallucinateEffectTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (y <= 25) {
			if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("hallucinationWhisper") >= 400) {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("hallucinationWhisper", 0);
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("hallucinationWhisperChance", Math.random());
				if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("hallucinationWhisperChance") < 0.2) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -20, 20), y + Mth.nextInt(RandomSource.create(), -3, 3), z + Mth.nextInt(RandomSource.create(), -20, 20)),
									BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:quiet_slow_whisper")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
						} else {
							_level.playLocalSound((x + Mth.nextInt(RandomSource.create(), -20, 20)), (y + Mth.nextInt(RandomSource.create(), -3, 3)), (z + Mth.nextInt(RandomSource.create(), -20, 20)),
									BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:quiet_slow_whisper")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
						}
					}
				} else {
					net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("hallucinationWhisperChance", Math.random());
					if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("hallucinationWhisperChance") < 0.1) {
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x + Mth.nextInt(RandomSource.create(), -20, 20), y + Mth.nextInt(RandomSource.create(), -3, 3), z + Mth.nextInt(RandomSource.create(), -20, 20)),
										BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:void_cave_creature")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
							} else {
								_level.playLocalSound((x + Mth.nextInt(RandomSource.create(), -20, 20)), (y + Mth.nextInt(RandomSource.create(), -3, 3)), (z + Mth.nextInt(RandomSource.create(), -20, 20)),
										BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:void_cave_creature")), SoundSource.HOSTILE, 3, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
							}
						}
					}
				}
			} else {
				net.sombrius.thedeepvoid.port.PortUtil.data(entity).putDouble("hallucinationWhisper", (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getDouble("hallucinationWhisper") + 1));
			}
		}
	}
}
