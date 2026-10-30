
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.procedures.RotOnEffectActiveTickProcedure;
import net.sombrius.thedeepvoid.procedures.RotActiveTickConditionProcedure;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class RotMobEffect extends MobEffect {
	public RotMobEffect() {
		super(MobEffectCategory.HARMFUL, -11188679);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		RotOnEffectActiveTickProcedure.execute(entity.level(), entity, amplifier);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return RotActiveTickConditionProcedure.execute(duration);
	}
}
