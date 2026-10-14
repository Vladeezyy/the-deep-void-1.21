
package net.sombrius.thedeepvoid.enchantment;

import java.util.List;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEnchantments;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class QuickTriggerEnchantment extends Enchantment {
	public QuickTriggerEnchantment(EquipmentSlot... slots) {
		super(Enchantment.definition(ItemTags.CROSSBOW_ENCHANTABLE, 2, 1, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(6, 10), 4, EquipmentSlot.MAINHAND));
	}

	@Override
	protected boolean checkCompatibility(Enchantment enchantment) {
		return this != enchantment && !List.of(TheDeepVoidModEnchantments.DIMENSIONAL_BURST.get()).contains(enchantment);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
		return Ingredient.of(new ItemStack(TheDeepVoidModItems.BLACK_HOLE.get())).test(itemstack);
	}

	@Override
	public boolean isTreasureOnly() {
		return true;
	}
}
