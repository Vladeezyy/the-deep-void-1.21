
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CookedVoidfishItem extends Item {
	public CookedVoidfishItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(4).saturationModifier(1f).build()));
	}
}
