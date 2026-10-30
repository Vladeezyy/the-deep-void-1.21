package net.sombrius.thedeepvoid.entity.layer;

import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import net.sombrius.thedeepvoid.entity.CentigazeHiddenEntity;

/** The glowing eyes: the layer texture drawn again with the eyes render type (full bright). */
public class CentigazeHiddenLayer<R extends LivingEntityRenderState & GeoRenderState> extends TextureLayerGeoLayer<CentigazeHiddenEntity, Void, R> {
	private static final Identifier LAYER = Identifier.fromNamespaceAndPath("the_deep_void", "textures/entities/eye_centipede_glow.png");

	public CentigazeHiddenLayer(GeoRenderer<CentigazeHiddenEntity, Void, R> entityRenderer) {
		super(entityRenderer, LAYER, RenderTypes::eyes);
	}
}
