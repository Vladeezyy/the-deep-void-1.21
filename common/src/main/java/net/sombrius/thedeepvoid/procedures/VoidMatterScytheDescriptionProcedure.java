package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class VoidMatterScytheDescriptionProcedure {
	public static String execute() {
		if (net.minecraft.client.Minecraft.getInstance().hasShiftDown()) {
			return Component.translatable("item.the_deep_void.void_matter_scythe.desc").getString();
		}
		return Component.translatable("item.the_deep_void.weapon_tooltip.show").getString();
	}
}
