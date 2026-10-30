
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.PenitentCrossLivingEntityIsHitWithToolProcedure;

public class PenitentCrossItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 2031, 2f, 0f, 15, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/rotten_bone")));

	public PenitentCrossItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().sword(TOOL_TIER, 17f, -3.7f).fireResistant());
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		super.hurtEnemy(itemstack, entity, sourceentity);
		PenitentCrossLivingEntityIsHitWithToolProcedure.execute(entity.level(), entity, sourceentity);
	}
}
