package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/** A player reeled in a catch (server); canceled, the hook brings nothing */
public class ItemFishedEvent extends PlayerEvent implements ICancellableEvent {
	private final List<ItemStack> drops;

	public ItemFishedEvent(Player player, List<ItemStack> drops) {
		super(player);
		this.drops = drops;
	}

	public List<ItemStack> getDrops() {
		return this.drops;
	}
}
