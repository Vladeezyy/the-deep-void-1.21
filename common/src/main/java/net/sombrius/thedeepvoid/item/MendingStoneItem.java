
package net.sombrius.thedeepvoid.item;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.MendingStoneItemInInventoryTickProcedure;

public class MendingStoneItem extends Item implements net.sombrius.thedeepvoid.port.PortItemHooks.ClientInventoryTick {
	public MendingStoneItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).fireResistant().rarity(Rarity.COMMON));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		MendingStoneItemInInventoryTickProcedure.execute(entity);
	}

	/** 1.21.5+: inventoryTick runs on the server only; the client ticks its player's items here (both sides did on 1.21.1) */
	@Override
	public void clientInventoryTick(ItemStack itemstack, net.minecraft.world.level.Level world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		MendingStoneItemInInventoryTickProcedure.execute(entity);
	}
}
