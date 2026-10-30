
package net.sombrius.thedeepvoid.potion;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.AbyssalMarkOnEffectActiveTickProcedure;
import net.sombrius.thedeepvoid.procedures.AbyssalMarkEffectExpiresProcedure;

import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class AbyssalMarkMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public AbyssalMarkMobEffect() {
		super(MobEffectCategory.NEUTRAL, -14935012);
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		AbyssalMarkOnEffectActiveTickProcedure.execute(entity, amplifier);
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		AbyssalMarkEffectExpiresProcedure.execute(entity.level(), entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
