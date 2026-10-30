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
public class LickerHookDescriptionProcedure {
	@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
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
		if (itemstack.getItem() == TheDeepVoidModItems.LICKER_HOOK.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr1").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr5").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr6").getString())));
		}
		if (itemstack.getItem() == TheDeepVoidModItems.GRIM_LICKER_HOOK.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.grim_licker_hook.descr1").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr1").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.grim_licker_hook.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr5").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr6").getString())));
		}
		if (itemstack.getItem() == TheDeepVoidModItems.VENGEFUL_LICKER_HOOK.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.grim_licker_hook.descr1").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr1").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.vengeful_licker_hook.descr1").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr5").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.licker_hook.descr6").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.vengeful_licker_hook.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.vengeful_licker_hook.descr3").getString())));
		}
	}
}
