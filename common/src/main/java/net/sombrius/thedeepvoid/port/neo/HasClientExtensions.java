package net.sombrius.thedeepvoid.port.neo;

/** The mod's items and effects with client extensions (each loader registers them from the mod's registries) */
public final class HasClientExtensions {
	private HasClientExtensions() {
	}

	public interface Item {
		IClientItemExtensions clientExtensions();
	}

	public interface Effect {
		IClientMobEffectExtensions clientExtensions();
	}
}
