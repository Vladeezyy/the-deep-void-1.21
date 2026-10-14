package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.minecraft.world.item.ItemStack;

public class VoidBowRightClickedProcedure {
	public static void execute(ItemStack itemstack) {
		{
			final String _tagName = "loading";
			final boolean _tagValue = true;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
		}
	}
}
