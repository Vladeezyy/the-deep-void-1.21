package net.sombrius.thedeepvoid.neoforge;

import net.neoforged.neoforge.items.IItemHandlerModifiable;

import net.minecraft.world.item.ItemStack;

import net.sombrius.thedeepvoid.port.neo.IItemHandler;

/** The mod's item handler as NeoForge's (for the capabilities other mods and hoppers ask for) */
final class NeoForgeItemHandler implements IItemHandlerModifiable {
	private final IItemHandler handler;

	private NeoForgeItemHandler(IItemHandler handler) {
		this.handler = handler;
	}

	static net.neoforged.neoforge.items.IItemHandler of(IItemHandler handler) {
		return handler == null ? null : new NeoForgeItemHandler(handler);
	}

	@Override
	public int getSlots() {
		return this.handler.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.handler.getStackInSlot(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return this.handler.insertItem(slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return this.handler.extractItem(slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		return this.handler.getSlotLimit(slot);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return this.handler.isItemValid(slot, stack);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		if (this.handler instanceof net.sombrius.thedeepvoid.port.neo.IItemHandlerModifiable modifiable)
			modifiable.setStackInSlot(slot, stack);
	}
}
