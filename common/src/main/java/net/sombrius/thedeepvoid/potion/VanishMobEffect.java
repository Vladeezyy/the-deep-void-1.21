
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.VanishOnEffectActiveTickProcedure;
import net.sombrius.thedeepvoid.procedures.VanishEffectStartedappliedProcedure;
import net.sombrius.thedeepvoid.procedures.VanishEffectExpiresProcedure;

import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class VanishMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public VanishMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -15132391);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		VanishEffectStartedappliedProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		VanishOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		VanishEffectExpiresProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
