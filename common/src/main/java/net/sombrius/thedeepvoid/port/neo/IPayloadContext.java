package net.sombrius.thedeepvoid.port.neo;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.CompletableFuture;

/** What a packet handler gets (NeoForge's interface, the methods the mod uses; both loaders implement it) */
public interface IPayloadContext {
	PacketFlow flow();

	/** The sender on the server, the local player on the client */
	Player player();

	/** Runs on the game thread of the receiving side */
	CompletableFuture<Void> enqueueWork(Runnable work);

	Connection connection();

	interface Connection {
		void disconnect(Component reason);
	}
}
