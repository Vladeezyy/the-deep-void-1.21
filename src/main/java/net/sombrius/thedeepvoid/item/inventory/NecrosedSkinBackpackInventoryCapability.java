package net.sombrius.thedeepvoid.item.inventory;

import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.items.ComponentItemHandler;
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

	public NecrosedSkinBackpackInventoryCapability(MutableDataComponentHolder parent) {
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
