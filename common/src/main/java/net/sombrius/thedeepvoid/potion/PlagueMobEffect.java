
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.procedures.PlagueOnEffectActiveTickProcedure;
import net.sombrius.thedeepvoid.procedures.PlagueActiveTickConditionProcedure;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class PlagueMobEffect extends MobEffect {
	public PlagueMobEffect() {
		super(MobEffectCategory.HARMFUL, -11358952);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		PlagueOnEffectActiveTickProcedure.execute(entity.level(), entity, amplifier);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return PlagueActiveTickConditionProcedure.execute(amplifier, duration);
	}
}
