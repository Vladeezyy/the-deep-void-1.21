package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class LavenditeSwordSpecialInformationProcedure {
	public static String execute() {
		if (Screen.hasShiftDown()) {
			return Component.translatable("item.the_deep_void.lavendite_sword.descr").getString();
		}
		return Component.translatable("item.the_deep_void.weapon_tooltip.show").getString();
	}
}
