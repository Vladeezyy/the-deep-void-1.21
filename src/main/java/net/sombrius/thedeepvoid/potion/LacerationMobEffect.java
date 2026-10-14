
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.LacerationEffectExpiresProcedure;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class LacerationMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public LacerationMobEffect() {
		super(MobEffectCategory.HARMFUL, -65022);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		LacerationEffectExpiresProcedure.execute(entity);
	}
}
