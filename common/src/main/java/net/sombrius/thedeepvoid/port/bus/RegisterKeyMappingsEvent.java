package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.client.KeyMapping;

import java.util.function.Consumer;

/** Registers key mappings (client setup) */
public class RegisterKeyMappingsEvent extends Event {
	private final Consumer<KeyMapping> sink;

	public RegisterKeyMappingsEvent(Consumer<KeyMapping> sink) {
		this.sink = sink;
	}

	public void register(KeyMapping key) {
		this.sink.accept(key);
	}
}
