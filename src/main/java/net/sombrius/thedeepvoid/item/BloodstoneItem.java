
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BloodstoneItem extends Item {
	public BloodstoneItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
