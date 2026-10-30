
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class WormToothItem extends Item {
	public WormToothItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
