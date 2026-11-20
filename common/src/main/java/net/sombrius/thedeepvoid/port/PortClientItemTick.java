package net.sombrius.thedeepvoid.port;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import net.sombrius.thedeepvoid.TheDeepVoidMod;
import net.sombrius.thedeepvoid.port.bus.ClientTickEvent;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;

/**
 * The client half of the items' inventory tick. 1.21.5+: ItemStack.inventoryTick calls the item on the server only, but
 * the MCreator procedures expect both sides (1.21.1): the client's copy moves its own player (Fly Wings and Soul Wings
 * flight, the Tombstone's jump, the Claw Scythe's dash, the Stinger). Same slots as Inventory.tick and
 * EntityEquipment.tick, before the player's tick.
 */
@EventBusSubscriber(modid = TheDeepVoidMod.MODID, value = Dist.CLIENT)
public final class PortClientItemTick {
	private PortClientItemTick() {
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event) {
		Minecraft mc = Minecraft.getInstance();
		LocalPlayer player = mc.player;
		if (player == null || mc.level == null || mc.isPaused())
			return;
		Inventory inventory = player.getInventory();
		for (int i = 0; i < inventory.getNonEquipmentItems().size(); i++) {
			ItemStack stack = inventory.getNonEquipmentItems().get(i);
			if (!stack.isEmpty() && stack.getItem() instanceof PortItemHooks.ClientInventoryTick item)
				item.clientInventoryTick(stack, mc.level, player, i == inventory.getSelectedSlot() ? EquipmentSlot.MAINHAND : null);
		}
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot == EquipmentSlot.MAINHAND)
				continue;
			ItemStack stack = player.getItemBySlot(slot);
			if (!stack.isEmpty() && stack.getItem() instanceof PortItemHooks.ClientInventoryTick item)
				item.clientInventoryTick(stack, mc.level, player, slot);
		}
	}
}
