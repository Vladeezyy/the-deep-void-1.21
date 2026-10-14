package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.entity.ChainedWeaverEntity;
import net.sombrius.thedeepvoid.entity.GiantBoneCrawlerEggEntity;
import net.sombrius.thedeepvoid.entity.HiveBrainEntity;
import net.sombrius.thedeepvoid.entity.IdlingApostleEntity;

public class TheBonkLivingEntityIsHitWithToolProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:bonk")), SoundSource.PLAYERS, 4, 1);
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:bonk")), SoundSource.PLAYERS, 4, 1, false);
			}
		}
		if (entity instanceof HiveBrainEntity || entity instanceof GiantBoneCrawlerEggEntity || entity instanceof IdlingApostleEntity || entity instanceof ChainedWeaverEntity) {
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
