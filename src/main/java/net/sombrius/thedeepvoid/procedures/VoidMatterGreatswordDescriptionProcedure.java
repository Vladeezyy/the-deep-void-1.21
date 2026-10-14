package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class VoidMatterGreatswordDescriptionProcedure {
	public static String execute() {
		if (Screen.hasShiftDown()) {
			return "\u00A77" + Component.translatable("item.the_deep_void.void_matter_greatsword.descr").getString();
		}
		return Component.translatable("item.the_deep_void.weapon_tooltip.show").getString();
	}
}
