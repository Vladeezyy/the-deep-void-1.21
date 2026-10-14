
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.MendingStoneItemInInventoryTickProcedure;

public class MendingStoneItem extends Item {
	public MendingStoneItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		MendingStoneItemInInventoryTickProcedure.execute(entity);
	}
}
