package net.sombrius.thedeepvoid.port.bus;

/** A frame was rendered (client; the bench scene counts them) */
public abstract class RenderFrameEvent extends Event {
	public static class Post extends RenderFrameEvent {
	}
}
