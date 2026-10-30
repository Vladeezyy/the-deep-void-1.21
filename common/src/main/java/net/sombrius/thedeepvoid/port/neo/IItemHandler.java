package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.world.item.ItemStack;

/** NeoForge's item handler interface (same methods), the common code's inventory view */
public interface IItemHandler {
	int getSlots();

	ItemStack getStackInSlot(int slot);

	ItemStack insertItem(int slot, ItemStack stack, boolean simulate);

	ItemStack extractItem(int slot, int amount, boolean simulate);

	int getSlotLimit(int slot);

	boolean isItemValid(int slot, ItemStack stack);
}
