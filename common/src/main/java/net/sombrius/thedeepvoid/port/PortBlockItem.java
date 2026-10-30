package net.sombrius.thedeepvoid.port;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

/** The mod's block items: since 1.21.5 blocks have no tooltips, the block item asks blocks that keep MCreator's */
public class PortBlockItem extends BlockItem {
	/** A block with MCreator's tooltip lines (client only) */
	public interface Tooltip {
		void appendHoverText(ItemStack itemstack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> list, TooltipFlag flag);
	}

	public PortBlockItem(Block block, Item.Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
		if (this.getBlock() instanceof Tooltip tooltip)
			tooltip.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
	}
}
