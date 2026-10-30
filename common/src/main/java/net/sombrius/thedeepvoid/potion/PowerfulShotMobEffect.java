
package net.sombrius.thedeepvoid.potion;

import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.sombrius.thedeepvoid.port.neo.IClientMobEffectExtensions;

public class PowerfulShotMobEffect extends MobEffect implements net.sombrius.thedeepvoid.port.neo.HasClientExtensions.Effect {
	public PowerfulShotMobEffect() {
		super(MobEffectCategory.NEUTRAL, -5148928);
	}

	public IClientMobEffectExtensions clientExtensions() {
		return new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance effect) {
				return false;
			}

			@Override
			public boolean extractInventoryText(MobEffectInstance instance, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int canvasWidth, int color) {
				return false;
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		};
	}
}
