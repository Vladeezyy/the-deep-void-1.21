package net.sombrius.thedeepvoid.procedures;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModMobEffects;

@EventBusSubscriber
public class BrainFogSendChatProcedure {
	@SubscribeEvent
	public static void onChat(ServerChatEvent event) {
		execute(event, event.getPlayer(), event.getRawText());
	}

	public static void execute(Entity entity, String text) {
		execute(null, entity, text);
	}

	private static void execute(@Nullable Event event, Entity entity, String text) {
		if (entity == null || text == null)
			return;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(TheDeepVoidModMobEffects.BRAIN_FOG)
				&& (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(TheDeepVoidModMobEffects.BRAIN_FOG) ? _livEnt.getEffect(TheDeepVoidModMobEffects.BRAIN_FOG).getAmplifier() : 0) >= 3) {
			if (event instanceof ICancellableEvent _cancellable) {
				_cancellable.setCanceled(true);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(("<" + entity.getDisplayName().getString() + "> " + text)), false);
		}
	}
}
