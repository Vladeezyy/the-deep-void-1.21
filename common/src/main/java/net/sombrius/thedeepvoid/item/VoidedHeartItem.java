
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.VoidedHeartRightclickedProcedure;

public class VoidedHeartItem extends Item {
	public VoidedHeartItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).fireResistant().rarity(Rarity.RARE));
	}

	/** NeoForge IItemExtension */
	public net.minecraft.world.item.ItemStackTemplate getCraftingRemainder(net.minecraft.world.item.ItemInstance instance) {
		return new net.minecraft.world.item.ItemStackTemplate(this);
	}

	/** Fabric FabricItem */
	public net.minecraft.world.item.ItemStackTemplate getCraftingRemainder(ItemStack itemstack) {
		return new net.minecraft.world.item.ItemStackTemplate(this);
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		VoidedHeartRightclickedProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, entity.getItemInHand(hand));
		return ar;
	}
}
