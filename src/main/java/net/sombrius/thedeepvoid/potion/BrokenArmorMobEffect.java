
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.procedures.BrokenArmorEffectStartedappliedProcedure;

public class BrokenArmorMobEffect extends MobEffect {
	public BrokenArmorMobEffect() {
		super(MobEffectCategory.HARMFUL, -13093847);
	}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		BrokenArmorEffectStartedappliedProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}
}
