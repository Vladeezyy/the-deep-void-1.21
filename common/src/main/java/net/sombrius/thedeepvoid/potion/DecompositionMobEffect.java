
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.procedures.DecompositionOnEffectActiveTickProcedure;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class DecompositionMobEffect extends MobEffect {
	public DecompositionMobEffect() {
		super(MobEffectCategory.HARMFUL, -14804711);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		DecompositionOnEffectActiveTickProcedure.execute(entity.level(), entity, amplifier);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
