
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.procedures.BismuthVialHasItemGlowingEffectProcedure;
import net.sombrius.thedeepvoid.procedures.BismuthVialRightclickedProcedure;

public class BismuthVialItem extends Item {
	public BismuthVialItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return BismuthVialHasItemGlowingEffectProcedure.execute(itemstack);
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		BismuthVialRightclickedProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}
}
