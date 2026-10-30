
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import net.sombrius.thedeepvoid.procedures.VoidSickleDescriptionProcedure;
import net.sombrius.thedeepvoid.procedures.ReapingHookEntitySwingsItemProcedure;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import java.util.List;

public class VoidSickleItem extends Item implements net.sombrius.thedeepvoid.port.PortItemHooks.EntitySwing {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 11f, 2f, 34, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/dark_steel_scrap")));

	public VoidSickleItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().fireResistant().sword(TOOL_TIER, 3, 0f));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, tooltipDisplay, list, flag);
		Entity entity = itemstack.getEntityRepresentation();
		list.accept(Component.literal(VoidSickleDescriptionProcedure.execute()));
	}

	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
		boolean retval = false;
		ReapingHookEntitySwingsItemProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
		return retval;
	}
}
