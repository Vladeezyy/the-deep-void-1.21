
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.procedures.DominateOnEffectActiveTickProcedure;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class DominateMobEffect extends MobEffect {
	public DominateMobEffect() {
		super(MobEffectCategory.NEUTRAL, -2565928);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		DominateOnEffectActiveTickProcedure.execute(entity, amplifier);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
