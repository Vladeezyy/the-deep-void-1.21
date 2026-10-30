
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BismuthItem extends Item {
	public BismuthItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
