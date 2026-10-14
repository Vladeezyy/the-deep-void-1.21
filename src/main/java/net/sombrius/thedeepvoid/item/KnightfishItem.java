
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class KnightfishItem extends Item {
	public KnightfishItem() {
		super(new Item.Properties().stacksTo(64).fireResistant().rarity(Rarity.RARE).food((new FoodProperties.Builder()).nutrition(-99).saturationModifier(-99f).alwaysEdible().build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 200;
	}
}
