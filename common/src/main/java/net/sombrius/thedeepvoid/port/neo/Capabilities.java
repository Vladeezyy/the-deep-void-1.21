package net.sombrius.thedeepvoid.port.neo;

/** The capability tokens the mod asks for ({@link PortCaps} answers them on both loaders) */
public final class Capabilities {
	private Capabilities() {
	}

	public static final class ItemHandler {
		public static final Token BLOCK = new Token("block");
		public static final Token ENTITY = new Token("entity");
		public static final Token ITEM = new Token("item");

		private ItemHandler() {
		}
	}

	public record Token(String name) {
	}
}
