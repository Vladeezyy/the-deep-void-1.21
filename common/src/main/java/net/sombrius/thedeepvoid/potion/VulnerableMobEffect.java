
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class VulnerableMobEffect extends MobEffect {
	public VulnerableMobEffect() {
		super(MobEffectCategory.HARMFUL, -12058624);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
