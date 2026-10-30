
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.WeaverOfSoulsTreasureBagRightclickedProcedure;

public class WeaverOfSoulsTreasureBagItem extends Item {
	public WeaverOfSoulsTreasureBagItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(64).rarity(Rarity.RARE));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		WeaverOfSoulsTreasureBagRightclickedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, entity.getItemInHand(hand));
		return ar;
	}
}
