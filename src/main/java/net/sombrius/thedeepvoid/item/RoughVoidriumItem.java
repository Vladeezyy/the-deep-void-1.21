
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RoughVoidriumItem extends Item {
	public RoughVoidriumItem() {
		super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE));
	}
}
