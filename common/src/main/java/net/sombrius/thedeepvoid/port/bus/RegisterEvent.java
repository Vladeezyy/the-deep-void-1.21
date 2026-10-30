package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

/**
 * Registers entries into a registry by hand (MCreator's armor materials). Posted once per registry key before the
 * mod's own entries of that registry; listeners check the key like NeoForge's {@code register(key, helper)}.
 */
public class RegisterEvent extends Event {
	private final ResourceKey<? extends Registry<?>> registryKey;
	private final Sink sink;

	public interface Sink {
		<T> void register(ResourceKey<? extends Registry<T>> registry, ResourceLocation id, T value);
	}

	public interface RegisterHelper<T> {
		void register(ResourceLocation id, T value);
	}

	public RegisterEvent(ResourceKey<? extends Registry<?>> registryKey, Sink sink) {
		this.registryKey = registryKey;
		this.sink = sink;
	}

	public ResourceKey<? extends Registry<?>> getRegistryKey() {
		return this.registryKey;
	}

	public <T> void register(ResourceKey<? extends Registry<T>> registry, Consumer<RegisterHelper<T>> consumer) {
		if (registry.equals(this.registryKey))
			consumer.accept((id, value) -> this.sink.register(registry, id, value));
	}
}
