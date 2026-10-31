
package net.sombrius.thedeepvoid.item;

import net.minecraft.world.item.ToolMaterial;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.ModifyDefaultComponentsEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class RottenShovelItem extends Item {
	private static final ToolMaterial TOOL_TIER = new ToolMaterial(BlockTags.INCORRECT_FOR_STONE_TOOL, 0, 9f, 0f, 22, net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "tool_materials/none")));

	public RottenShovelItem() {
		super(net.sombrius.thedeepvoid.port.platform.Registrar.itemProps().shovel(TOOL_TIER, 2.5f, -2.9f));
	}

	@SubscribeEvent
	public static void handleToolDamage(ModifyDefaultComponentsEvent event) {
		event.modify(TheDeepVoidModItems.ROTTEN_SHOVEL.get(), builder -> builder.remove(DataComponents.MAX_DAMAGE));
	}
}
