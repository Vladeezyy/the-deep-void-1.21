
package net.sombrius.thedeepvoid.potion;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.AdaptedEffectExpiresProcedure;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.AdaptedEffectStartedappliedProcedure;

public class AdaptedMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public AdaptedMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16748642);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		AdaptedEffectStartedappliedProcedure.execute(entity);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		AdaptedEffectExpiresProcedure.execute(entity);
	}
}
