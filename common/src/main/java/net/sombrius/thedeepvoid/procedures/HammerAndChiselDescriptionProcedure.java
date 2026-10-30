package net.sombrius.thedeepvoid.procedures;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class HammerAndChiselDescriptionProcedure {
	public static String execute() {
		if (net.minecraft.client.Minecraft.getInstance().hasShiftDown()) {
			return "\u00A77" + Component.translatable("item.the_deep_void.hammer_and_chisel.desc").getString();
		}
		return Component.translatable("item.the_deep_void.weapon_tooltip.show").getString();
	}
}
