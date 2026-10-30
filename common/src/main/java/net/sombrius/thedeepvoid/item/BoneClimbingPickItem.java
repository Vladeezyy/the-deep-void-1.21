
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.BoneClimbingPickRightclickedOnBlockProcedure;

public class BoneClimbingPickItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 8f, 0f, 10, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/onyx")));

	public BoneClimbingPickItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().sword(TOOL_TIER, 4f, -2.6f).fireResistant());
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		BoneClimbingPickRightclickedOnBlockProcedure.execute(context.getLevel(), context.getPlayer(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}
}
