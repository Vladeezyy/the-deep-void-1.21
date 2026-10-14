package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class LivingScytheDescriptionProcedure {
	public static String execute() {
		if (Screen.hasShiftDown()) {
			return "\u00A77" + Component.translatable("item.the_deep_void.living_scythe.desc").getString();
		}
		return Component.translatable("item.the_deep_void.weapon_tooltip.show").getString();
	}
}
