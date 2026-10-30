
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.procedures.IchorBurnOnEffectActiveTickProcedure;
import net.sombrius.thedeepvoid.procedures.IchorBurnActiveTickConditionProcedure;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class IchorBurnMobEffect extends MobEffect {
	public IchorBurnMobEffect() {
		super(MobEffectCategory.HARMFUL, -9728);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		IchorBurnOnEffectActiveTickProcedure.execute(entity.level(), entity, amplifier);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return IchorBurnActiveTickConditionProcedure.execute(duration);
	}
}
