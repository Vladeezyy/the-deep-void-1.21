package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

/** A list of stacks as an item handler, saved like NeoForge's ({@code Items} with {@code Slot}, {@code Size}) */
public class ItemStackHandler implements IItemHandlerModifiable {
	protected NonNullList<ItemStack> stacks;

	public ItemStackHandler() {
		this(1);
	}

	public ItemStackHandler(int size) {
		this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	public ItemStackHandler(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	public void setSize(int size) {
		this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		validateSlotIndex(slot);
		this.stacks.set(slot, stack);
		onContentsChanged(slot);
	}

	@Override
	public int getSlots() {
		return this.stacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		validateSlotIndex(slot);
		return this.stacks.get(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;
		if (!isItemValid(slot, stack))
			return stack;
		validateSlotIndex(slot);
		ItemStack existing = this.stacks.get(slot);
		int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
		if (!existing.isEmpty()) {
			if (!ItemStack.isSameItemSameComponents(stack, existing))
				return stack;
			limit -= existing.getCount();
		}
		if (limit <= 0)
			return stack;
		boolean reachedLimit = stack.getCount() > limit;
		if (!simulate) {
			if (existing.isEmpty())
				this.stacks.set(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
			else
				existing.grow(reachedLimit ? limit : stack.getCount());
			onContentsChanged(slot);
		}
		return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount == 0)
			return ItemStack.EMPTY;
		validateSlotIndex(slot);
		ItemStack existing = this.stacks.get(slot);
		if (existing.isEmpty())
			return ItemStack.EMPTY;
		int toExtract = Math.min(amount, existing.getMaxStackSize());
		if (existing.getCount() <= toExtract) {
			if (!simulate) {
				this.stacks.set(slot, ItemStack.EMPTY);
				onContentsChanged(slot);
				return existing;
			}
			return existing.copy();
		}
		if (!simulate) {
			this.stacks.set(slot, existing.copyWithCount(existing.getCount() - toExtract));
			onContentsChanged(slot);
		}
		return existing.copyWithCount(toExtract);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 99;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return true;
	}

	public CompoundTag serializeNBT(HolderLookup.Provider provider) {
		ListTag items = new ListTag();
		for (int i = 0; i < this.stacks.size(); i++) {
			if (!this.stacks.get(i).isEmpty()) {
				CompoundTag item = new CompoundTag();
				item.putInt("Slot", i);
				items.add(ItemStack.CODEC.encode(this.stacks.get(i), provider.createSerializationContext(net.minecraft.nbt.NbtOps.INSTANCE), item).getOrThrow());
			}
		}
		CompoundTag nbt = new CompoundTag();
		nbt.put("Items", items);
		nbt.putInt("Size", this.stacks.size());
		return nbt;
	}

	public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
		setSize(nbt.getIntOr("Size", this.stacks.size()));
		ListTag items = nbt.getListOrEmpty("Items");
		for (int i = 0; i < items.size(); i++) {
			CompoundTag item = items.getCompoundOrEmpty(i);
			int slot = item.getIntOr("Slot", 0);
			if (slot >= 0 && slot < this.stacks.size())
				ItemStack.CODEC.parse(provider.createSerializationContext(net.minecraft.nbt.NbtOps.INSTANCE), item).result().ifPresent(stack -> this.stacks.set(slot, stack));
		}
		onLoad();
	}

	protected void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= this.stacks.size())
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.stacks.size() + ")");
	}

	protected void onLoad() {
	}

	protected void onContentsChanged(int slot) {
	}
}
