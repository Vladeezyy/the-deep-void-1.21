
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.RottenHeartEffectStartedappliedProcedure;
import net.sombrius.thedeepvoid.procedures.RottenHeartOnEffectActiveTickProcedure;

public class RottenHeartMobEffect extends MobEffect {
	public RottenHeartMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -14145496);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		RottenHeartEffectStartedappliedProcedure.execute(entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		RottenHeartOnEffectActiveTickProcedure.execute(entity.level(), entity);
		return super.applyEffectTick(entity, amplifier);
	}
}
