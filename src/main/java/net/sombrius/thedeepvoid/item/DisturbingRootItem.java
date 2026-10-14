
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class DisturbingRootItem extends Item {
	public DisturbingRootItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 32;
	}
}
