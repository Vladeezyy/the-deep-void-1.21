package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.minecraft.world.item.ItemStack;

public class IchorGreatswordRightClickProcedure {
	public static void execute(ItemStack itemstack) {
		{
			final String _tagName = "block";
			final double _tagValue = 1;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
		}
	}
}
