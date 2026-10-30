
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
import net.sombrius.thedeepvoid.procedures.BoneSwordLivingEntityIsHitWithToolProcedure;

public class DarkSteelPickaxeItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2464, 12f, 0f, 8, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/dark_steel_scrap")));

	public DarkSteelPickaxeItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().pickaxe(TOOL_TIER, 6f, -2.8f).fireResistant());
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		super.hurtEnemy(itemstack, entity, sourceentity);
		BoneSwordLivingEntityIsHitWithToolProcedure.execute(entity);
	}
}
