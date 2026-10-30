
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.HandsOfTheAberrationOnEffectActiveTickProcedure;

public class HandsOfTheAberrationMobEffect extends MobEffect {
	public HandsOfTheAberrationMobEffect() {
		super(MobEffectCategory.HARMFUL, -7038559);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		HandsOfTheAberrationOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(level, entity, amplifier);
	}
}
