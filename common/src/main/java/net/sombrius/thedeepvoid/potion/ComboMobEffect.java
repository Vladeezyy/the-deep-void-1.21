
package net.sombrius.thedeepvoid.potion;

import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.sombrius.thedeepvoid.port.neo.IClientMobEffectExtensions;

public class ComboMobEffect extends MobEffect implements net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Effect {
	public ComboMobEffect() {
		super(MobEffectCategory.HARMFUL, -4690689);
	}

	public IClientMobEffectExtensions clientExtensions() {
		return new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance effect) {
				return false;
			}

			@Override
			public boolean renderInventoryText(MobEffectInstance instance, AbstractContainerScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
				return false;
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		};
	}
}
