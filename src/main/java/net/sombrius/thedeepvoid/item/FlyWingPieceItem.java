
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class FlyWingPieceItem extends Item {
	public FlyWingPieceItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
