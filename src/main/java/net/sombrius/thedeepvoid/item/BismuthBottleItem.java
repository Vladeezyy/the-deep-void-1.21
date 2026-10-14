
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.sombrius.thedeepvoid.procedures.BismuthBottleRightClickedProcedure;
import net.sombrius.thedeepvoid.procedures.BismuthVialHasItemGlowingEffectProcedure;

public class BismuthBottleItem extends Item {
	public BismuthBottleItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return BismuthVialHasItemGlowingEffectProcedure.execute(itemstack);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		BismuthBottleRightClickedProcedure.execute(world, entity, ar.getObject());
		return ar;
	}
}
