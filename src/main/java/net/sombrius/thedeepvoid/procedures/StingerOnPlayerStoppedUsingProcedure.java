package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class StingerOnPlayerStoppedUsingProcedure {
	public static void execute(ItemStack itemstack) {
		{
			final String _tagName = "rightClicked";
			final boolean _tagValue = false;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
		}
		{
			final String _tagName = "state";
			final double _tagValue = 0;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
		}
		{
			final String _tagName = "hit";
			final double _tagValue = 0;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putDouble(_tagName, _tagValue));
		}
	}
}
