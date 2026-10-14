package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class ForbiddenFruitPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity.getPersistentData().getBoolean("tastedForbiddenFruit") == false) {
			entity.getPersistentData().putBoolean("tastedForbiddenFruit", true);
		}
		if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL)) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL);
		} else {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL, 400, 0, true, true));
		}
	}
}
