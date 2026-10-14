
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.BarbedTipsActiveTickConditionProcedure;
import net.sombrius.thedeepvoid.procedures.BarbedTipsOnEffectActiveTickProcedure;

public class BarbedTipsMobEffect extends MobEffect {
	public BarbedTipsMobEffect() {
		super(MobEffectCategory.HARMFUL, -11511961);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		BarbedTipsOnEffectActiveTickProcedure.execute(entity.level(), entity, amplifier);
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return BarbedTipsActiveTickConditionProcedure.execute(duration);
	}
}
