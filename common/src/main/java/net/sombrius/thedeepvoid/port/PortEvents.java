package net.sombrius.thedeepvoid.port;

import net.sombrius.thedeepvoid.port.bus.MobEffectEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;

import net.minecraft.world.effect.MobEffectInstance;

/** Game event hooks that stand in for 1.20 methods 1.21 removed. */
@EventBusSubscriber
public class PortEvents {
	@SubscribeEvent
	public static void onEffectRemoved(MobEffectEvent.Remove event) {
		effectEnded(event.getEntity(), event.getEffectInstance());
	}

	@SubscribeEvent
	public static void onEffectExpired(MobEffectEvent.Expired event) {
		effectEnded(event.getEntity(), event.getEffectInstance());
	}

	private static void effectEnded(net.minecraft.world.entity.LivingEntity entity, MobEffectInstance instance) {
		if (instance != null && instance.getEffect().value() instanceof PortUtil.EffectRemovedListener listener)
			listener.onEffectRemoved(entity, instance.getAmplifier());
	}
}
