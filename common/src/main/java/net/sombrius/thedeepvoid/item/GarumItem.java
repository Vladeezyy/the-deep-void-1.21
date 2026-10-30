
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.GarumPlayerFinishesUsingItemProcedure;

public class GarumItem extends Item {
	public GarumItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(16).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0f).build()));
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack itemstack) {
		return ItemUseAnimation.DRINK;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		GarumPlayerFinishesUsingItemProcedure.execute(entity);
		return retval;
	}
}
