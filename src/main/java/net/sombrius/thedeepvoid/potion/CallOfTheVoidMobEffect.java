
package net.sombrius.thedeepvoid.potion;

import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.CallOfTheVoidEffectExpiresProcedure;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.CallOfTheVoidOnEffectActiveTickProcedure;

public class CallOfTheVoidMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public CallOfTheVoidMobEffect() {
		super(MobEffectCategory.HARMFUL, -1);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		CallOfTheVoidOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		CallOfTheVoidEffectExpiresProcedure.execute(entity.level(), entity);
	}
}
