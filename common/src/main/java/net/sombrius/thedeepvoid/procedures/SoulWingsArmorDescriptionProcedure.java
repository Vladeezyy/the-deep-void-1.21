package net.sombrius.thedeepvoid.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.ItemTooltipEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber(value = {Dist.CLIENT})
public class SoulWingsArmorDescriptionProcedure {
	@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		execute(event, event.getItemStack(), event.getToolTip());
	}

	public static void execute(ItemStack itemstack, List<Component> tooltip) {
		execute(null, itemstack, tooltip);
	}

	private static void execute(@Nullable Event event, ItemStack itemstack, List<Component> tooltip) {
		if (tooltip == null)
			return;
		if (itemstack.getItem() == TheDeepVoidModItems.SOUL_WINGS_CHESTPLATE.get() || itemstack.getItem() == TheDeepVoidModItems.SOUL_WINGS_FLY_CHESTPLATE.get()) {
			tooltip.add(Component.literal(""));
			if (net.minecraft.client.Minecraft.getInstance().hasShiftDown()) {
				tooltip.add(Component.literal(("\u00A76" + Component.translatable("armors.the_deep_void.key_press").getString())));
				tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.soul_wings.on_key_press1").getString())));
				tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.soul_wings.on_key_press2").getString())));
				tooltip.add(Component.literal(("\u00A76" + Component.translatable("armors.the_deep_void.soul_wings.when_worn").getString())));
				tooltip.add(Component.literal((" \u00A77-" + Component.translatable("armors.the_deep_void.soul_wings.worn").getString())));
			} else {
				tooltip.add(Component.literal((Component.translatable("armors.the_deep_void.press_shift").getString())));
			}
		}
	}
}
