package net.sombrius.thedeepvoid.procedures;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

public class CookedFleshPlayerFinishesUsingItemProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (Math.random() < 0.01) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(TheDeepVoidModMobEffects.BLOSSOM, 100, 0));
		}
	}
}
