package net.sombrius.thedeepvoid.procedures;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber(value = {Dist.CLIENT})
public class RotTemplateDescriptionProcedure {
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
		if (itemstack.getItem() == TheDeepVoidModItems.SMITHING_TEMPLATE_ROT.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_rot.descr1").getString())));
			tooltip.add(Component.literal(" "));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_rot.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_rot.descr3").getString())));
			tooltip.add(Component.literal(" "));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_rot.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.smithing_template_rot.descr5").getString())));
		}
	}
}
