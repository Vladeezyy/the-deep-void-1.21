package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.GiantShadowHandModel;
import net.sombrius.thedeepvoid.entity.layer.GiantShadowHandLayer;
import net.sombrius.thedeepvoid.entity.GiantShadowHandEntity;

public class GiantShadowHandRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<GiantShadowHandEntity, R> {
	public GiantShadowHandRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new GiantShadowHandModel());
		this.shadowRadius = 0f;
		withRenderLayer(new GiantShadowHandLayer<>(this));
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
