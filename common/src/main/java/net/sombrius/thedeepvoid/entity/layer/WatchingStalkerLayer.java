package net.sombrius.thedeepvoid.entity.layer;

import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import net.sombrius.thedeepvoid.entity.WatchingStalkerEntity;

/** The glowing eyes: the layer texture drawn again with the eyes render type (full bright). */
public class WatchingStalkerLayer<R extends LivingEntityRenderState & GeoRenderState> extends TextureLayerGeoLayer<WatchingStalkerEntity, Void, R> {
	private static final Identifier LAYER = Identifier.fromNamespaceAndPath("the_deep_void", "textures/entities/stalkernew_eyes.png");

	public WatchingStalkerLayer(GeoRenderer<WatchingStalkerEntity, Void, R> entityRenderer) {
		super(entityRenderer, LAYER, RenderTypes::eyes);
	}
}
