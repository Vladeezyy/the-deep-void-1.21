
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.TheBonkLivingEntityIsHitWithToolProcedure;

@EventBusSubscriber
public class TheBonkItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 0, 99f, 0f, 99, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/none")));

	public TheBonkItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().sword(TOOL_TIER, 998f, 95f).fireResistant());
	}

	@SubscribeEvent
	public static void handleToolDamage(ModifyDefaultComponentsEvent event) {
		event.modify(TheDeepVoidModItems.THE_BONK.get(), builder -> builder.remove(DataComponents.MAX_DAMAGE));
	}

	@Override
	public void hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
		super.hurtEnemy(itemstack, entity, sourceentity);
		TheBonkLivingEntityIsHitWithToolProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
	}

	@Override
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay, java.util.function.Consumer<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, context, tooltipDisplay, list, flag);
		list.accept(Component.translatable("text.the_deep_void.port.creative_mode_only"));
		list.accept(Component.translatable("text.the_deep_void.port.removes_boss_spawns_completely_hive_s_brain"));
	}
}
