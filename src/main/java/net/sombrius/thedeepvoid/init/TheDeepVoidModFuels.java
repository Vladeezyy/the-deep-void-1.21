
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.sombrius.thedeepvoid.init;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

@EventBusSubscriber
public class TheDeepVoidModFuels {
	@SubscribeEvent
	public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
		ItemStack itemstack = event.getItemStack();
		if (itemstack.getItem() == TheDeepVoidModItems.SULFUR.get())
			event.setBurnTime(3400);
	}
}
