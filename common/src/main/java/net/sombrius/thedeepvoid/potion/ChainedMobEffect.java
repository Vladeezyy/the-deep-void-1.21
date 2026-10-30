
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.ChainedEffectStartedappliedProcedure;
import net.sombrius.thedeepvoid.procedures.ChainedOnEffectActiveTickProcedure;

public class ChainedMobEffect extends MobEffect {
	public ChainedMobEffect() {
		super(MobEffectCategory.NEUTRAL, -15526371);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		ChainedEffectStartedappliedProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ());
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		ChainedOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}
