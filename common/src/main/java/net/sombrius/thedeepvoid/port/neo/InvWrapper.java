package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

/** A vanilla container as an item handler */
public class InvWrapper implements IItemHandlerModifiable {
	private final Container inv;

	public InvWrapper(Container inv) {
		this.inv = inv;
	}

	public Container getInv() {
		return this.inv;
	}

	@Override
	public int getSlots() {
		return this.inv.getContainerSize();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inv.getItem(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return ItemHandlers.insert(this, slot, stack, simulate, this.inv.getItem(slot), this::setAndChange, Math.min(this.inv.getMaxStackSize(), stack.getMaxStackSize()),
				this.inv.canPlaceItem(slot, stack));
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return ItemHandlers.extract(slot, amount, simulate, this.inv.getItem(slot), this::setAndChange);
	}

	private void setAndChange(int slot, ItemStack stack) {
		this.inv.setItem(slot, stack);
		this.inv.setChanged();
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		this.inv.setItem(slot, stack);
	}

	@Override
	public int getSlotLimit(int slot) {
		return this.inv.getMaxStackSize();
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return this.inv.canPlaceItem(slot, stack);
	}
}
