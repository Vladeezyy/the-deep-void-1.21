
package net.sombrius.thedeepvoid.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SunderMobEffect extends MobEffect {
	public SunderMobEffect() {
		super(MobEffectCategory.HARMFUL, -4704457);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
