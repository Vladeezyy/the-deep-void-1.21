
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

public class LavenditePickaxeItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 12, 40f, 0f, 22, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/lavendite")));

	public LavenditePickaxeItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().pickaxe(TOOL_TIER, 2f, -1f));
	}
}
