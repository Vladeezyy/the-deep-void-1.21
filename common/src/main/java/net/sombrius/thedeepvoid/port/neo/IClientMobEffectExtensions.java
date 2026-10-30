package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffectInstance;

/** NeoForge's client mob effect extensions, the methods the mod overrides (client only) */
public interface IClientMobEffectExtensions {
	default boolean isVisibleInInventory(MobEffectInstance instance) {
		return true;
	}

	default boolean isVisibleInGui(MobEffectInstance instance) {
		return true;
	}

	default boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
		return false;
	}
}
