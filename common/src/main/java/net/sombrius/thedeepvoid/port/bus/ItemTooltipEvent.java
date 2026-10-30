package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

/** The tooltip lines of a stack (client); add to {@link #getToolTip()} */
public class ItemTooltipEvent extends Event {
	private final ItemStack itemStack;
	@Nullable
	private final Player player;
	private final List<Component> toolTip;
	private final TooltipFlag flags;
	private final Item.TooltipContext context;

	public ItemTooltipEvent(ItemStack itemStack, @Nullable Player player, List<Component> toolTip, TooltipFlag flags, Item.TooltipContext context) {
		this.itemStack = itemStack;
		this.player = player;
		this.toolTip = toolTip;
		this.flags = flags;
		this.context = context;
	}

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	public List<Component> getToolTip() {
		return this.toolTip;
	}

	@Nullable
	public Player getEntity() {
		return this.player;
	}

	public TooltipFlag getFlags() {
		return this.flags;
	}

	public Item.TooltipContext getContext() {
		return this.context;
	}
}
