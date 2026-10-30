package net.sombrius.thedeepvoid.procedures;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;

public class NecrosedSkinBackpackPlaceItemConditionProcedure {
	public static boolean execute(ItemStack itemstack) {
		if (itemstack.is(net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.parse("the_deep_void:containers")))) {
			return true;
		}
		return false;
	}
}
