package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;

/** Registers the sky and fog effects of a dimension type's {@code effects} id (client setup) */
public class RegisterDimensionSpecialEffectsEvent extends Event {
	private final BiConsumer<Identifier, DimensionSpecialEffects> sink;

	public RegisterDimensionSpecialEffectsEvent(BiConsumer<Identifier, DimensionSpecialEffects> sink) {
		this.sink = sink;
	}

	public void register(Identifier id, DimensionSpecialEffects effects) {
		net.sombrius.thedeepvoid.port.PortClient.DIMENSION_EFFECTS.add(effects);
		this.sink.accept(id, effects);
	}
}
