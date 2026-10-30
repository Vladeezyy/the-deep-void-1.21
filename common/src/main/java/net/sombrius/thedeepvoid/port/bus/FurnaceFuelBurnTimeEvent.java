package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.item.ItemStack;

/** The burn time of a stack in a furnace; -1 is the vanilla value */
public class FurnaceFuelBurnTimeEvent extends Event implements ICancellableEvent {
	private final ItemStack itemStack;
	private int burnTime;

	public FurnaceFuelBurnTimeEvent(ItemStack itemStack, int burnTime) {
		this.itemStack = itemStack;
		this.burnTime = burnTime;
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	public int getBurnTime() {
		return this.burnTime;
	}

	public void setBurnTime(int burnTime) {
		if (burnTime >= 0) {
			this.burnTime = burnTime;
			this.setCanceled(true);
		}
	}
}
