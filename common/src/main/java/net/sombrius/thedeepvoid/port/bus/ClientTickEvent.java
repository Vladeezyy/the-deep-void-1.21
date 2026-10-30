package net.sombrius.thedeepvoid.port.bus;

public abstract class ClientTickEvent extends Event {
	/** Before the client ticks */
	public static class Pre extends ClientTickEvent {
	}

	public static class Post extends ClientTickEvent {
	}
}
