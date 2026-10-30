
package net.sombrius.thedeepvoid.potion;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.HexedOnEffectActiveTickProcedure;
import net.sombrius.thedeepvoid.procedures.HexedEffectExpiresProcedure;

import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class HexedMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public HexedMobEffect() {
		super(MobEffectCategory.HARMFUL, -14567726);
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		HexedOnEffectActiveTickProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, amplifier);
		return super.applyEffectTick(level, entity, amplifier);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		HexedEffectExpiresProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
