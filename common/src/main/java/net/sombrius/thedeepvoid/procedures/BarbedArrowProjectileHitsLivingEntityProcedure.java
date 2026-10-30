package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class BarbedArrowProjectileHitsLivingEntityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.arrow.hit")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3));
			} else {
				_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.arrow.hit")), SoundSource.PLAYERS, 1, (float) Mth.nextDouble(RandomSource.create(), 12 / 11, 4 / 3), false);
			}
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.BARBED_TIPS.holder(), 60,
					(int) ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.BARBED_TIPS.holder()) ? _livEnt.getEffect(TheDeepVoidModMobEffects.BARBED_TIPS.holder()).getAmplifier() : 0) + 1)));
		if (entity instanceof Player) {
			if (entity instanceof Player _plr6)
				_plr6.setArrowCount((int) ((entity instanceof Player _plr5 ? _plr5.getArrowCount() : 0) + 1));
		}
	}
}
