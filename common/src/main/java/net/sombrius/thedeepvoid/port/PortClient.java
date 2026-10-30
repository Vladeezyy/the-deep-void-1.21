package net.sombrius.thedeepvoid.port;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.level.material.Fluid;

import java.util.function.BiConsumer;

/** Client calls that differ between the loaders (set by the loader's client entry point) */
public final class PortClient {
	/** NeoForge's {@code net.sombrius.thedeepvoid.port.PortClient.setRenderLayer(Fluid, RenderType)} */
	public static BiConsumer<Fluid, RenderType> fluidRenderLayer = (fluid, type) -> {
	};

	/** The dimension effects the mod registered (RegisterDimensionSpecialEffectsEvent; both loaders add them) */
	public static final java.util.List<net.minecraft.client.renderer.DimensionSpecialEffects> DIMENSION_EFFECTS = new java.util.ArrayList<>();

	private PortClient() {
	}

	public static java.util.List<net.minecraft.client.renderer.DimensionSpecialEffects> dimensionEffects() {
		return DIMENSION_EFFECTS;
	}

	public static void setRenderLayer(Fluid fluid, RenderType type) {
		fluidRenderLayer.accept(fluid, type);
	}
}
