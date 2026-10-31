
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class MaggotStewItem extends Item {
	public MaggotStewItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(16).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(5).saturationModifier(0.3f).build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 28;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = new ItemStack(Items.BOWL);
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
