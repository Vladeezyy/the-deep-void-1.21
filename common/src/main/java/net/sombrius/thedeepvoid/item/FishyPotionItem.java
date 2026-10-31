
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class FishyPotionItem extends Item {
	public FishyPotionItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0f).alwaysEdible().build()));
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack itemstack) {
		return ItemUseAnimation.DRINK;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = new ItemStack(Items.GLASS_BOTTLE);
		super.finishUsingItem(itemstack, world, entity);
		if (itemstack.isEmpty()) {
			return retval;
		} else {
			if (entity instanceof Player player && !player.getAbilities().instabuild) {
				if (!player.getInventory().add(retval))
					player.drop(retval, false, net.minecraft.util.Prediction.SERVER_ONLY);
			}
			return itemstack;
		}
	}
}
