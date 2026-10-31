
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.CallUponTheHivePlayerFinishesUsingItemProcedure;
import net.sombrius.thedeepvoid.procedures.CallUponTheHiveRightclickedOnBlockProcedure;

public class CallUponTheHiveItem extends Item {
	public CallUponTheHiveItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0f).alwaysEdible().build()));
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack itemstack) {
		return ItemUseAnimation.SPEAR;
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 20;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = new ItemStack(TheDeepVoidModItems.CALL_UPON_THE_HIVE.get());
		super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		CallUponTheHivePlayerFinishesUsingItemProcedure.execute(world, entity);
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

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		CallUponTheHiveRightclickedOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}
}
