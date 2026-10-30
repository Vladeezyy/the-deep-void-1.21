package net.sombrius.thedeepvoid.procedures;

import net.minecraft.core.component.DataComponents;

import net.sombrius.thedeepvoid.port.bus.ICancellableEvent;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.port.bus.LivingEntityUseItemEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;

@EventBusSubscriber
public class PlayerTriesToUseItemAfterForbiddenFruitProcedure {
	@SubscribeEvent
	public static void onUseItemStart(LivingEntityUseItemEvent.Start event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getItem());
		}
	}

	public static void execute(Entity entity, ItemStack itemstack) {
		execute(null, entity, itemstack);
	}

	private static void execute(@Nullable Event event, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (itemstack.has(DataComponents.FOOD) && !(itemstack.getItem() == TheDeepVoidModItems.FORBIDDEN_FRUIT.get()) && net.sombrius.thedeepvoid.port.PortUtil.data(entity).getBooleanOr("tastedForbiddenFruit", false) == true) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
		}
	}
}
