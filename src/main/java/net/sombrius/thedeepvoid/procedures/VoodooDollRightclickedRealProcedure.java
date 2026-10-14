package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class VoodooDollRightclickedRealProcedure {
	public static void execute(ItemStack itemstack) {
		{
			final String _tagName = "deep_void:rightClicking";
			final boolean _tagValue = true;
			CustomData.update(DataComponents.CUSTOM_DATA, itemstack, tag -> tag.putBoolean(_tagName, _tagValue));
		}
	}
}
