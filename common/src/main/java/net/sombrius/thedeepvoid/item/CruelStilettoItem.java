
package net.sombrius.thedeepvoid.item;

import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import net.sombrius.thedeepvoid.procedures.CruelStilettoToolInHandTickProcedure;
import net.sombrius.thedeepvoid.procedures.CruelStilettoSpecialInformationProcedure;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;

import java.util.List;

public class CruelStilettoItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 10f, 3f, 10, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/void_matter")));

	public CruelStilettoItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().fireResistant().sword(TOOL_TIER, 3, -3f));
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext level, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, level, tooltipDisplay, list, flag);
		Entity entity = itemstack.getEntityRepresentation();
		list.accept(Component.literal(CruelStilettoSpecialInformationProcedure.execute()));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, ServerLevel world, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
		super.inventoryTick(itemstack, world, entity, equipmentSlot);
		boolean selected = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
		int slot = equipmentSlot == null ? -1 : equipmentSlot.getIndex();
		if (selected)
			CruelStilettoToolInHandTickProcedure.execute(entity);
	}
}
