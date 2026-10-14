package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class PlaguebringerLivingEntityIsHitWithToolProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double level = 0;
		double duration = 0;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.PLAGUE)) {
			level = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.PLAGUE) ? _livEnt.getEffect(TheDeepVoidModMobEffects.PLAGUE).getAmplifier() : 0;
			duration = entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.PLAGUE) ? _livEnt.getEffect(TheDeepVoidModMobEffects.PLAGUE).getDuration() : 0;
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.PLAGUE);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.PLAGUE, (int) Math.round(duration * 0.8), (int) (level + 1)));
		}
	}
}
