
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
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
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		AmbrosiaWithdrawalOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		AmbrosiaWithdrawalEffectExpiresProcedure.execute(entity);
	}
}
