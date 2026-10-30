package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.GrabbingHandsModel;
import net.sombrius.thedeepvoid.entity.GrabbingHandsEntity;

public class GrabbingHandsRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<GrabbingHandsEntity, R> {
	public GrabbingHandsRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new GrabbingHandsModel());
		this.shadowRadius = 1f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
