package net.sombrius.thedeepvoid.port.neo;

/** The config types (the mod has one common config) */
public final class ModConfig {
	private ModConfig() {
	}

	public enum Type {
		COMMON, CLIENT, SERVER, STARTUP
	}
}
