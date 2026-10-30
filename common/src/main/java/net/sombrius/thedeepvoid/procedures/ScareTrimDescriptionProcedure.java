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
public class ScareTrimDescriptionProcedure {
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
		if (itemstack.getItem() == TheDeepVoidModItems.SCARE_ARMOR_TRIM.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scare_armor_trim.descr1").getString())));
			tooltip.add(Component.literal(" "));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scare_armor_trim.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scare_armor_trim.descr3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scare_armor_trim.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scare_armor_trim.descr5").getString())));
		}
		if (itemstack.getItem() == TheDeepVoidModItems.SCAVENGER_ARMOR_TRIM.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scavenger_armor_trim.descr1").getString())));
			tooltip.add(Component.literal(" "));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scavenger_armor_trim.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scavenger_armor_trim.descr3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scavenger_armor_trim.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.scavenger_armor_trim.descr5").getString())));
		}
		if (itemstack.getItem() == TheDeepVoidModItems.SORROW_ARMOR_TRIM.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.sorrow_armor_trim.descr1").getString())));
			tooltip.add(Component.literal(" "));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.sorrow_armor_trim.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.sorrow_armor_trim.descr3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.sorrow_armor_trim.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.sorrow_armor_trim.descr5").getString())));
		}
		if (itemstack.getItem() == TheDeepVoidModItems.GORE_ARMOR_TRIM.get()) {
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.gore_armor_trim.descr1").getString())));
			tooltip.add(Component.literal(" "));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.gore_armor_trim.descr2").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.gore_armor_trim.descr3").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.gore_armor_trim.descr4").getString())));
			tooltip.add(Component.literal((Component.translatable("item.the_deep_void.gore_armor_trim.descr5").getString())));
		}
	}
}
