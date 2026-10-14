
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CookedBloodfinItem extends Item {
	public CookedBloodfinItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(2).saturationModifier(2f).build()));
	}
}
