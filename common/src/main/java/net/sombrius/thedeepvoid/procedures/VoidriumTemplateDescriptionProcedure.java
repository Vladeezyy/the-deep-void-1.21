package net.sombrius.thedeepvoid.procedures;

import java.util.List;
import javax.annotation.Nullable;
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
public class VoidriumTemplateDescriptionProcedure {
	@OnlyIn(Dist.CLIENT)
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
		if (itemstack.getItem() == TheDeepVoidModItems.SMITHING_TEMPLATE_VOIDRIUM.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_voidrium.desc1").getString())));
			tooltip.add(Component.literal(" "));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_voidrium.desc2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_voidrium.desc3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_voidrium.desc4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_voidrium.desc5").getString())));
		}
	}
}
