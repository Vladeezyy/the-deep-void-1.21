package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SoulCleaverSpecialInformationProcedure {
	public static String execute() {
		if (net.minecraft.client.Minecraft.getInstance().hasShiftDown()) {
			return Component.translatable("item.the_deep_void.soul_cleaver.descr").getString();
		}
		return Component.translatable("item.the_deep_void.weapon_tooltip.show").getString();
	}
}
