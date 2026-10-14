
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.HeavyOnEffectActiveTickProcedure;

public class HeavyMobEffect extends MobEffect {
	public HeavyMobEffect() {
		super(MobEffectCategory.HARMFUL, -14474461);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		HeavyOnEffectActiveTickProcedure.execute(entity);
		return super.applyEffectTick(entity, amplifier);
	}
}
