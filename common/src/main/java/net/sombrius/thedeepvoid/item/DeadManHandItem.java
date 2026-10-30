
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.DeadManHandRightclickedProcedure;

public class DeadManHandItem extends Item {
	public DeadManHandItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		DeadManHandRightclickedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity.getItemInHand(hand));
		return ar;
	}
}
