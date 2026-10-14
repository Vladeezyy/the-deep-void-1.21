
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BloodfinItem extends Item {
	public BloodfinItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(1).saturationModifier(2f).build()));
	}
}
