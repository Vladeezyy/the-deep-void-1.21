
package net.sombrius.thedeepvoid.enchantment;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class ConservationEnchantment extends Enchantment {
	public ConservationEnchantment(EquipmentSlot... slots) {
		super(Enchantment.definition(ItemTags.WEAPON_ENCHANTABLE, 2, 1, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(6, 10), 4, EquipmentSlot.MAINHAND));
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
		return Ingredient.of(new ItemStack(TheDeepVoidModItems.ONYX_FLARE_GUN.get())).test(itemstack);
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}

	@Override
	public boolean isTradeable() {
		return false;
	}
}
