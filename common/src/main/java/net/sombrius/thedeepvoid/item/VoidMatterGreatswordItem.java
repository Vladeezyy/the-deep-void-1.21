
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.VoidMatterGreatswordDescriptionProcedure;
import net.sombrius.thedeepvoid.procedures.VoidMatterGreatswordEntitySwingsItemProcedure;
import net.sombrius.thedeepvoid.procedures.VoidMatterGreatswordLivingEntityIsHitWithToolProcedure;

public class VoidMatterGreatswordItem extends Item implements net.sombrius.thedeepvoid.port.PortItemHooks.EntitySwing {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 1561, 10f, 0f, 10, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/void_matter")));

	public VoidMatterGreatswordItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().sword(TOOL_TIER, 9f, -3.2f).fireResistant());
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		super.hurtEnemy(itemstack, entity, sourceentity);
		VoidMatterGreatswordLivingEntityIsHitWithToolProcedure.execute(entity);
	}

	@Override
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
		Entity entity = itemstack.getEntityRepresentation() != null ? itemstack.getEntityRepresentation() : Minecraft.getInstance().player;
		list.accept(Component.literal(VoidMatterGreatswordDescriptionProcedure.execute()));
	}

	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
		boolean retval = false;
		VoidMatterGreatswordEntitySwingsItemProcedure.execute(entity.level(), entity, itemstack);
		return retval;
	}
}
