package net.sombrius.thedeepvoid.entity.layer;

import software.bernie.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import net.sombrius.thedeepvoid.entity.GiantShadowHandEntity;

/** The glowing eyes: the layer texture drawn again with the eyes render type (full bright). */
public class GiantShadowHandLayer<R extends LivingEntityRenderState & GeoRenderState> extends TextureLayerGeoLayer<GiantShadowHandEntity, Void, R> {
	private static final Identifier LAYER = Identifier.fromNamespaceAndPath("the_deep_void", "textures/entities/giantshadowhand_glow.png");

	public GiantShadowHandLayer(GeoRenderer<GiantShadowHandEntity, Void, R> entityRenderer) {
		super(entityRenderer, LAYER, RenderTypes::eyes);
	}
}
