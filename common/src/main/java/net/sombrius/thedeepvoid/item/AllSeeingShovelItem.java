
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class AllSeeingShovelItem extends ShovelItem {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 460, 8f, 0f, 10, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/refined_cinnabar")));

	public AllSeeingShovelItem() {
		super(TOOL_TIER, 4.5f, -3f, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().fireResistant());
	}
}
