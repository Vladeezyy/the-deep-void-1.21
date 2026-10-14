
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class GooBallItem extends Item {
	public GooBallItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
