
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.port.PortUtil;

import net.sombrius.thedeepvoid.procedures.ParasitizedEffectExpiresProcedure;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ParasitizedMobEffect extends MobEffect implements PortUtil.EffectRemovedListener {
	public ParasitizedMobEffect() {
		super(MobEffectCategory.HARMFUL, -933251);
	}

	@Override
	public void onEffectRemoved(LivingEntity entity, int amplifier) {
		ParasitizedEffectExpiresProcedure.execute(entity.level(), entity);
	}
}
