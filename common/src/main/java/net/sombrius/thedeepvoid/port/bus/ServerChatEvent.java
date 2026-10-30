package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/** A player sent a chat message (server); canceling it drops the message */
public class ServerChatEvent extends Event implements ICancellableEvent {
	private final ServerPlayer player;
	private final String rawText;
	private Component message;

	public ServerChatEvent(ServerPlayer player, String rawText, Component message) {
		this.player = player;
		this.rawText = rawText;
		this.message = message;
	}

	public ServerPlayer getPlayer() {
		return this.player;
	}

	public String getUsername() {
		return this.player.getGameProfile().name();
	}

	public String getRawText() {
		return this.rawText;
	}

	public Component getMessage() {
		return this.message;
	}

	public void setMessage(Component message) {
		this.message = message;
	}
}
