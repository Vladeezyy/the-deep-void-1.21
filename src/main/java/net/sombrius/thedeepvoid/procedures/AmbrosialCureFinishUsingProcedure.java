package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class AmbrosialCureFinishUsingProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double level = 0;
		double duration = 0;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL)) {
			level = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL) ? _livEnt.getEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL).getAmplifier() : 0;
			duration = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL) ? _livEnt.getEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL).getDuration() : 0;
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL, (int) (duration - 250), (int) (level - 1), true, true));
		}
	}
}
