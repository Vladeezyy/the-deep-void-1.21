
package net.sombrius.thedeepvoid.item;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.procedures.SpyglassDummyItemInHandTickProcedure;

public class SpyglassDummyItem extends Item {
	public SpyglassDummyItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().stacksTo(1).rarity(Rarity.COMMON));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		if (selected)
			SpyglassDummyItemInHandTickProcedure.execute(entity, itemstack);
	}
}
