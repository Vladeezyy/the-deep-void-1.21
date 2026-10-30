
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import net.sombrius.thedeepvoid.procedures.StalkerScytheClawLivingEntityIsHitWithToolProcedure;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;

public class StalkerScytheClawItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 3021, 8.5f, 8f, 15, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/rotten_bone")));

	public StalkerScytheClawItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().fireResistant().sword(TOOL_TIER, 3, -2.8f));
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		super.hurtEnemy(itemstack, entity, sourceentity);
		StalkerScytheClawLivingEntityIsHitWithToolProcedure.execute(entity.level(), entity);
	}
}
