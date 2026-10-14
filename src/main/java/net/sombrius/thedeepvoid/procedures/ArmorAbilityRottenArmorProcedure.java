package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class ArmorAbilityRottenArmorProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).RottenArmorAbility == 0) {
			{
				TheDeepVoidModVariables.PlayerVariables _vars = entity.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
				_vars.RottenArmorAbility = 460;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.ROTTEN_HEART, 240, 2));
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.PLAYERS, (float) 1.5, 1);
				} else {
					_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("particle.soul_escape")), SoundSource.PLAYERS, (float) 1.5, 1, false);
				}
			}
		}
	}
}
