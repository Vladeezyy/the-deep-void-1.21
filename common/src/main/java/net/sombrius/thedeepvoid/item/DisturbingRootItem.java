
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class DisturbingRootItem extends Item {
	public DisturbingRootItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 32;
	}
}
