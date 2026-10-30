package net.sombrius.thedeepvoid.procedures;

import net.sombrius.thedeepvoid.port.bus.ICancellableEvent;

import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;
import net.sombrius.thedeepvoid.port.bus.Event;
import net.sombrius.thedeepvoid.port.bus.LivingEntityUseItemEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class DebilitatedUsesItemsProcedure {
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
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.DEBILITATED.holder())) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 30);
		}
		if (entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(TheDeepVoidModMobEffects.TERROR.holder())) {
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack, 160);
			if (itemstack.getItem() instanceof BowItem || itemstack.getItem() instanceof CrossbowItem) {
				if (event instanceof ICancellableEvent _cancellable) {
					_cancellable.setCanceled(true);
				}
			}
		}
	}
}
