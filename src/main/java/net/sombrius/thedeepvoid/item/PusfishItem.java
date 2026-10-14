
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.PusfishPlayerFinishesUsingItemProcedure;

public class PusfishItem extends Item {
	public PusfishItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(5).saturationModifier(1f).build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 26;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		PusfishPlayerFinishesUsingItemProcedure.execute(entity);
		return retval;
	}
}
