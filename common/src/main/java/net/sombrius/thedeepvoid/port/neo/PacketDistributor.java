package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import net.sombrius.thedeepvoid.port.platform.Platform;

/** NeoForge's static send methods, on the loader's networking */
public final class PacketDistributor {
	private PacketDistributor() {
	}

	public static void sendToServer(CustomPacketPayload payload) {
		Platform.INSTANCE.sendToServer(payload);
	}

	public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
		Platform.INSTANCE.sendToPlayer(player, payload);
	}

	public static void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload) {
		Platform.INSTANCE.sendToPlayersInDimension(level, payload);
	}

	public static void sendToAllPlayers(CustomPacketPayload payload) {
		Platform.INSTANCE.sendToAllPlayers(payload);
	}
}
