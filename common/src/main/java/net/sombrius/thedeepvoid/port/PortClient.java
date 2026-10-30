package net.sombrius.thedeepvoid.port;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.material.Fluid;

import java.util.function.BiConsumer;

/** Client calls that differ between the loaders (set by the loader's client entry point) */
public final class PortClient {
	/** NeoForge's {@code net.sombrius.thedeepvoid.port.PortClient.setRenderLayer(Fluid, ChunkSectionLayer)} */
	public static BiConsumer<Fluid, ChunkSectionLayer> fluidRenderLayer = (fluid, type) -> {
	};

	/** The mod's key mapping category (1.21.9: an id; the name is key.category.the_deep_void.deep_void) */
	public static final net.minecraft.client.KeyMapping.Category KEY_CATEGORY = net.minecraft.client.KeyMapping.Category
			.register(net.minecraft.resources.Identifier.fromNamespaceAndPath("the_deep_void", "deep_void"));

	private PortClient() {
	}

	public static void setRenderLayer(Fluid fluid, ChunkSectionLayer type) {
		fluidRenderLayer.accept(fluid, type);
	}
}
