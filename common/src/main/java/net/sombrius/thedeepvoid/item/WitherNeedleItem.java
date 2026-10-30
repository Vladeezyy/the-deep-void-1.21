
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.WitherNeedleClickProcedure;

public class WitherNeedleItem extends Item {
	public WitherNeedleItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		WitherNeedleClickProcedure.execute(world, entity, entity.getItemInHand(hand));
		return ar;
	}
}
