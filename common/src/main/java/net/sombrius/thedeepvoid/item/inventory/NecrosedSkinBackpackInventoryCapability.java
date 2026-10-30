package net.sombrius.thedeepvoid.item.inventory;

import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.ItemTossEvent;
import net.sombrius.thedeepvoid.port.neo.ComponentItemHandler;
import net.sombrius.thedeepvoid.client.gui.NecrosedSkinBackpackGUIScreen;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber(Dist.CLIENT)
public class NecrosedSkinBackpackInventoryCapability extends ComponentItemHandler {
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == TheDeepVoidModItems.NECROSED_SKIN_BACKPACK.get()) {
			if (Minecraft.getInstance().screen instanceof NecrosedSkinBackpackGUIScreen) {
				Minecraft.getInstance().player.closeContainer();
			}
		}
	}

	public NecrosedSkinBackpackInventoryCapability(ItemStack parent) {
		super(parent, DataComponents.CONTAINER, 40);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return stack.getItem() != TheDeepVoidModItems.NECROSED_SKIN_BACKPACK.get();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return super.getStackInSlot(slot).copy();
	}
}
