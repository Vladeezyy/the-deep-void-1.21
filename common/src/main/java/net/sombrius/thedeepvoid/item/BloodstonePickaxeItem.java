
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public class BloodstonePickaxeItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2400, 7f, 3f, 8, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/bloodstone")));

	public BloodstonePickaxeItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().pickaxe(TOOL_TIER, 1, -2.6f));
	}
}
