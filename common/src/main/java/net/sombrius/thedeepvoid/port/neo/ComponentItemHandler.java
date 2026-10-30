package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

/** The slots of an item stack's container component as an item handler (each change writes the component) */
public class ComponentItemHandler implements IItemHandlerModifiable {
	protected final ItemStack parent;
	protected final DataComponentType<ItemContainerContents> component;
	protected final int size;

	public ComponentItemHandler(ItemStack parent, DataComponentType<ItemContainerContents> component, int size) {
		this.parent = parent;
		this.component = component;
		this.size = size;
	}

	protected ItemContainerContents getContents() {
		return this.parent.getOrDefault(this.component, ItemContainerContents.EMPTY);
	}

	protected NonNullList<ItemStack> copyStacks() {
		NonNullList<ItemStack> stacks = NonNullList.withSize(this.size, ItemStack.EMPTY);
		getContents().copyInto(stacks);
		return stacks;
	}

	protected void setStacks(NonNullList<ItemStack> stacks, int changedSlot) {
		this.parent.set(this.component, ItemContainerContents.fromItems(stacks));
		onContentsChanged(changedSlot, stacks.get(changedSlot));
	}

	@Override
	public int getSlots() {
		return this.size;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return copyStacks().get(slot);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		NonNullList<ItemStack> stacks = copyStacks();
		stacks.set(slot, stack);
		setStacks(stacks, slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack.isEmpty() || !isItemValid(slot, stack))
			return stack;
		NonNullList<ItemStack> stacks = copyStacks();
		ItemStack existing = stacks.get(slot);
		int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
		if (!existing.isEmpty()) {
			if (!ItemStack.isSameItemSameComponents(stack, existing))
				return stack;
			limit -= existing.getCount();
		}
		if (limit <= 0)
			return stack;
		int moved = Math.min(limit, stack.getCount());
		if (!simulate) {
			stacks.set(slot, existing.isEmpty() ? stack.copyWithCount(moved) : existing.copyWithCount(existing.getCount() + moved));
			setStacks(stacks, slot);
		}
		return moved == stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - moved);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		NonNullList<ItemStack> stacks = copyStacks();
		ItemStack existing = stacks.get(slot);
		if (amount <= 0 || existing.isEmpty())
			return ItemStack.EMPTY;
		int taken = Math.min(amount, Math.min(existing.getCount(), existing.getMaxStackSize()));
		if (!simulate) {
			stacks.set(slot, existing.getCount() == taken ? ItemStack.EMPTY : existing.copyWithCount(existing.getCount() - taken));
			setStacks(stacks, slot);
		}
		return existing.copyWithCount(taken);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 99;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return stack.getItem().canFitInsideContainerItems();
	}

	protected void onContentsChanged(int slot, ItemStack stack) {
	}
}
