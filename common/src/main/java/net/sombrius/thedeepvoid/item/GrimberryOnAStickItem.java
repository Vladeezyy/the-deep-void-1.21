
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.InteractionResult;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.GrimberryOnAStickItemInInventoryTickProcedure;
import net.sombrius.thedeepvoid.procedures.GrimberryOnAStickRightclickedProcedure;

public class GrimberryOnAStickItem extends Item {
	public GrimberryOnAStickItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().durability(35).rarity(Rarity.COMMON));
	}

	@Override
	public InteractionResult use(Level world, Player entity, InteractionHand hand) {
		InteractionResult ar = super.use(world, entity, hand);
		GrimberryOnAStickRightclickedProcedure.execute(entity, entity.getItemInHand(hand));
		return ar;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		GrimberryOnAStickItemInInventoryTickProcedure.execute(entity, itemstack);
	}
}
