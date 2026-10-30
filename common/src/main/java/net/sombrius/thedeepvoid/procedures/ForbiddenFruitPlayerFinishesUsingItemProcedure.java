package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class ForbiddenFruitPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBoolean("tastedForbiddenFruit") == false) {
			net.sombrius.thedeepvoid.port.PortUtil.data(entity).putBoolean("tastedForbiddenFruit", true);
		}
		if (entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder())) {
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder());
		} else {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.AMBROSIA_WITHDRAWAL.holder(), 400, 0, true, true));
		}
	}
}
