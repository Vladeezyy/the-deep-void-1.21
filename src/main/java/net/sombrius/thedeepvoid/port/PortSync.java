package net.sombrius.thedeepvoid.port;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;

import net.minecraft.server.level.ServerPlayer;

import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

/**
 * One player variables packet per player tick: the procedures that run on the player tick sync the whole player
 * variables (about 80 fields and 4 item stacks) after every change, up to 8 times a tick. While the server ticks a
 * player, {@link #defer} only marks them changed, and the last listener of the tick sends them once.
 */
@EventBusSubscriber(modid = TheDeepVoidMod.MODID)
public final class PortSync {
	private static ServerPlayer ticking;
	private static boolean changed;

	private PortSync() {
	}

	/** True when the sync of this player's variables is left to the end of its tick. */
	public static boolean defer(ServerPlayer player) {
		if (player != ticking)
			return false;
		changed = true;
		return true;
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void playerTickFirst(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player) {
			ticking = player;
			changed = false;
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void playerTickLast(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player && player == ticking) {
			ticking = null;
			if (changed) {
				changed = false;
				player.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).syncPlayerVariables(player);
			}
		}
	}
}
