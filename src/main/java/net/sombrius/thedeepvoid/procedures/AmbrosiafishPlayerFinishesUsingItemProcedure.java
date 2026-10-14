package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class AmbrosiafishPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity)
			_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) + 2.5));
		if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL))) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL, 600, 0));
		} else {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL, 600,
						(int) ((entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL) ? _livEnt.getEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL).getAmplifier() : 0) + 1)));
		}
	}
}
