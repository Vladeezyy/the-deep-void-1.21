
package net.sombrius.thedeepvoid.potion;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.DebilitatedEffectExpiresProcedure;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.DebilitatedEffectStartedappliedProcedure;

public class DebilitatedMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public DebilitatedMobEffect() {
		super(MobEffectCategory.NEUTRAL, -11122637);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		DebilitatedEffectStartedappliedProcedure.execute(entity);
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		DebilitatedEffectExpiresProcedure.execute(entity);
	}
}
