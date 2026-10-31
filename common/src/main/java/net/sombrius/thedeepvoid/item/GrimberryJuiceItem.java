
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
import net.sombrius.thedeepvoid.procedures.GrimberryJuicePlayerFinishesUsingItemProcedure;

public class GrimberryJuiceItem extends Item {
	public GrimberryJuiceItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(4).saturationModifier(0.2f).alwaysEdible().build()));
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack itemstack) {
		return ItemUseAnimation.DRINK;
	}

	/** NeoForge IItemExtension */
	public net.minecraft.world.item.ItemStackTemplate getCraftingRemainder(net.minecraft.world.item.ItemInstance instance) {
		return new net.minecraft.world.item.ItemStackTemplate(Items.GLASS_BOTTLE);
	}

	/** Fabric FabricItem */
	public net.minecraft.world.item.ItemStackTemplate getCraftingRemainder(ItemStack itemstack) {
		return new net.minecraft.world.item.ItemStackTemplate(Items.GLASS_BOTTLE);
	}

	@Override
	public int getUseDuration(ItemStack itemstack, LivingEntity entity) {
		return 15;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
		ItemStack retval = new ItemStack(Items.GLASS_BOTTLE);
		super.finishUsingItem(itemstack, world, entity);
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		GrimberryJuicePlayerFinishesUsingItemProcedure.execute(entity);
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
