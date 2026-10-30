package net.sombrius.thedeepvoid.entity.layer;

import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.GeoRenderState;

import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import net.sombrius.thedeepvoid.entity.StalkerEntity;

/** The glowing eyes: the layer texture drawn again with the eyes render type (full bright). */
public class StalkerLayer<R extends LivingEntityRenderState & GeoRenderState> extends TextureLayerGeoLayer<StalkerEntity, Void, R> {
	private static final Identifier LAYER = Identifier.fromNamespaceAndPath("the_deep_void", "textures/entities/stalkernew_eyesnsouls.png");

	public StalkerLayer(GeoRenderer<StalkerEntity, Void, R> entityRenderer) {
		super(entityRenderer, LAYER, RenderTypes::eyes);
	}
}
