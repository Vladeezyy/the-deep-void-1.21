
package net.sombrius.thedeepvoid.enchantment;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class CrateCatcherEnchantment extends Enchantment {
	public CrateCatcherEnchantment(EquipmentSlot... slots) {
		super(Enchantment.definition(ItemTags.FISHING_ENCHANTABLE, 2, 3, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(6, 10), 4, EquipmentSlot.MAINHAND));
	}
}
