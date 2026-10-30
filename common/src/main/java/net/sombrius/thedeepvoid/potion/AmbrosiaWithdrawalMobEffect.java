
package net.sombrius.thedeepvoid.potion;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.AmbrosiaWithdrawalEffectExpiresProcedure;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.AmbrosiaWithdrawalOnEffectActiveTickProcedure;

public class AmbrosiaWithdrawalMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public AmbrosiaWithdrawalMobEffect() {
		super(MobEffectCategory.HARMFUL, -14792947);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		AmbrosiaWithdrawalOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		AmbrosiaWithdrawalEffectExpiresProcedure.execute(entity);
	}
}
