
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import net.sombrius.thedeepvoid.procedures.VoidriumSwordLivingEntityIsHitWithToolProcedure;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;

public class VoidriumShovelItem extends ShovelItem {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 3461, 34f, 5.5f, 20, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/voidrium_gem")));

	public VoidriumShovelItem() {
		super(TOOL_TIER, 1, -3f, net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().fireResistant());
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		super.hurtEnemy(itemstack, entity, sourceentity);
		VoidriumSwordLivingEntityIsHitWithToolProcedure.execute(entity);
	}
}
