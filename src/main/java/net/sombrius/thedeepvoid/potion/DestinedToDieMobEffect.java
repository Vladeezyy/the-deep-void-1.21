
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class DestinedToDieMobEffect extends MobEffect {
	public DestinedToDieMobEffect() {
		super(MobEffectCategory.NEUTRAL, -14221245);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
}
