package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.world.item.ItemStack;

/** Insert / extract on a raw slot (shared by the container wrappers) */
final class ItemHandlers {
	interface Setter {
		void set(int slot, ItemStack stack);
	}

	private ItemHandlers() {
	}

	static ItemStack insert(IItemHandler handler, int slot, ItemStack stack, boolean simulate, ItemStack existing, Setter setter, int limit, boolean valid) {
		if (stack.isEmpty())
			return ItemStack.EMPTY;
		if (!existing.isEmpty()) {
			if (existing.getCount() >= limit || !ItemStack.isSameItemSameComponents(stack, existing))
				return stack;
			if (!valid)
				return stack;
			int space = limit - existing.getCount();
			if (stack.getCount() <= space) {
				if (!simulate)
					setter.set(slot, existing.copyWithCount(existing.getCount() + stack.getCount()));
				return ItemStack.EMPTY;
			}
			if (!simulate)
				setter.set(slot, existing.copyWithCount(limit));
			return stack.copyWithCount(stack.getCount() - space);
		}
		if (!valid)
			return stack;
		if (stack.getCount() <= limit) {
			if (!simulate)
				setter.set(slot, stack.copy());
			return ItemStack.EMPTY;
		}
		if (!simulate)
			setter.set(slot, stack.copyWithCount(limit));
		return stack.copyWithCount(stack.getCount() - limit);
	}

	static ItemStack extract(int slot, int amount, boolean simulate, ItemStack existing, Setter setter) {
		if (amount <= 0 || existing.isEmpty())
			return ItemStack.EMPTY;
		if (simulate)
			return existing.copyWithCount(Math.min(existing.getCount(), amount));
		int taken = Math.min(existing.getCount(), amount);
		setter.set(slot, existing.getCount() == taken ? ItemStack.EMPTY : existing.copyWithCount(existing.getCount() - taken));
		return existing.copyWithCount(taken);
	}
}
