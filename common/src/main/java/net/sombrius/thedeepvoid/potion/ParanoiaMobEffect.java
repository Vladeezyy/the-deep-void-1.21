
package net.sombrius.thedeepvoid.potion;

import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.sombrius.thedeepvoid.port.neo.IClientMobEffectExtensions;
import net.sombrius.thedeepvoid.procedures.ParanoiaOnEffectActiveTickProcedure;

public class ParanoiaMobEffect extends MobEffect implements net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Effect {
	public ParanoiaMobEffect() {
		super(MobEffectCategory.HARMFUL, -12763843);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		ParanoiaOnEffectActiveTickProcedure.execute(entity);
		return super.applyEffectTick(entity, amplifier);
	}

	public IClientMobEffectExtensions clientExtensions() {
		return new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance effect) {
				return false;
			}

			@Override
			public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
				return false;
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		};
	}
}
