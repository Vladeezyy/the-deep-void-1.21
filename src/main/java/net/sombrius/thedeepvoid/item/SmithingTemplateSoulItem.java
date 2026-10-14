
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class SmithingTemplateSoulItem extends Item {
	public SmithingTemplateSoulItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
