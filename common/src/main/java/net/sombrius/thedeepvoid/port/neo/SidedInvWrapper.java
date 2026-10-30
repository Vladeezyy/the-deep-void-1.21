package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/** The slots of a worldly container that one side exposes, as an item handler */
public class SidedInvWrapper implements IItemHandlerModifiable {
	private final WorldlyContainer inv;
	@Nullable
	private final Direction side;

	public SidedInvWrapper(WorldlyContainer inv, @Nullable Direction side) {
		this.inv = inv;
		this.side = side;
	}

	private int slot(int index) {
		if (this.side == null)
			return index;
		int[] slots = this.inv.getSlotsForFace(this.side);
		return index < slots.length ? slots[index] : -1;
	}

	@Override
	public int getSlots() {
		return this.side == null ? this.inv.getContainerSize() : this.inv.getSlotsForFace(this.side).length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		int slot = slot(index);
		return slot < 0 ? ItemStack.EMPTY : this.inv.getItem(slot);
	}

	@Override
	public ItemStack insertItem(int index, ItemStack stack, boolean simulate) {
		int slot = slot(index);
		if (slot < 0 || !this.inv.canPlaceItemThroughFace(slot, stack, this.side))
			return stack;
		return ItemHandlers.insert(this, slot, stack, simulate, this.inv.getItem(slot), this::setAndChange, Math.min(this.inv.getMaxStackSize(), stack.getMaxStackSize()),
				this.inv.canPlaceItem(slot, stack));
	}

	@Override
	public ItemStack extractItem(int index, int amount, boolean simulate) {
		int slot = slot(index);
		if (slot < 0 || !this.inv.canTakeItemThroughFace(slot, this.inv.getItem(slot), this.side))
			return ItemStack.EMPTY;
		return ItemHandlers.extract(slot, amount, simulate, this.inv.getItem(slot), this::setAndChange);
	}

	private void setAndChange(int slot, ItemStack stack) {
		this.inv.setItem(slot, stack);
		this.inv.setChanged();
	}

	@Override
	public void setStackInSlot(int index, ItemStack stack) {
		int slot = slot(index);
		if (slot >= 0)
			this.inv.setItem(slot, stack);
	}

	@Override
	public int getSlotLimit(int index) {
		return this.inv.getMaxStackSize();
	}

	@Override
	public boolean isItemValid(int index, ItemStack stack) {
		int slot = slot(index);
		return slot >= 0 && this.inv.canPlaceItem(slot, stack);
	}
}
