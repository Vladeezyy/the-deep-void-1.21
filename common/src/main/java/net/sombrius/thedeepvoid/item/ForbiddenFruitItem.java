
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.ForbiddenFruitPlayerFinishesUsingItemProcedure;

public class ForbiddenFruitItem extends Item {
	public ForbiddenFruitItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.UNCOMMON).food((new FoodProperties.Builder()).nutrition(5).saturationModifier(1f).build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 38;
	}

	@Override
	@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = new ItemStack(TheDeepVoidModItems.FORBIDDEN_FRUIT.get());
		super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		ForbiddenFruitPlayerFinishesUsingItemProcedure.execute(entity);
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
