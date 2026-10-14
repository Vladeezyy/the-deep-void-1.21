package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;

import net.minecraft.world.item.ItemStack;

public class BallAndChainItemInInventoryTickProcedure {
	public static void execute(ItemStack itemstack) {
		if ((itemstack.getDisplayName().getString()).contains("Rem's Morningstar") && itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("secret") == 0) {
			{
				final String _tagName = "secret";
				final double _tagValue = 1;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
			}
		} else if (!(itemstack.getDisplayName().getString()).contains("Rem's Morningstar") && itemstack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("secret") == 1) {
			{
				final String _tagName = "secret";
				final double _tagValue = 0;
				CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
			}
		}
	}
}
