package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** A menu slot over an item handler */
public class SlotItemHandler extends Slot {
	private static final Container EMPTY = new SimpleContainer(0);
	private final IItemHandler itemHandler;
	private final int index;

	public SlotItemHandler(IItemHandler itemHandler, int index, int x, int y) {
		super(EMPTY, index, x, y);
		this.itemHandler = itemHandler;
		this.index = index;
	}

	public IItemHandler getItemHandler() {
		return this.itemHandler;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return !stack.isEmpty() && this.itemHandler.isItemValid(this.index, stack);
	}

	@Override
	public ItemStack getItem() {
		return this.itemHandler.getStackInSlot(this.index);
	}

	@Override
	public void set(ItemStack stack) {
		((IItemHandlerModifiable) this.itemHandler).setStackInSlot(this.index, stack);
		this.setChanged();
	}

	public void initialize(ItemStack stack) {
		((IItemHandlerModifiable) this.itemHandler).setStackInSlot(this.index, stack);
		this.setChanged();
	}

	@Override
	public void setChanged() {
	}

	@Override
	public int getMaxStackSize() {
		return this.itemHandler.getSlotLimit(this.index);
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		ItemStack maxAdd = stack.copyWithCount(stack.getMaxStackSize());
		int maxInput = stack.getMaxStackSize();
		ItemStack current = this.itemHandler.getStackInSlot(this.index);
		if (this.itemHandler instanceof IItemHandlerModifiable handler) {
			handler.setStackInSlot(this.index, ItemStack.EMPTY);
			ItemStack remainder = handler.insertItem(this.index, maxAdd, true);
			handler.setStackInSlot(this.index, current);
			return maxInput - remainder.getCount();
		}
		ItemStack remainder = this.itemHandler.insertItem(this.index, maxAdd, true);
		return current.getCount() + maxInput - remainder.getCount();
	}

	@Override
	public boolean mayPickup(Player player) {
		return !this.itemHandler.extractItem(this.index, 1, true).isEmpty();
	}

	@Override
	public ItemStack remove(int amount) {
		return this.itemHandler.extractItem(this.index, amount, false);
	}
}
