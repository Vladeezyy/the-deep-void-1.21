package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.SummonedShadowHandModel;
import net.sombrius.thedeepvoid.entity.SummonedShadowHandEntity;

public class SummonedShadowHandRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<SummonedShadowHandEntity, R> {
	public SummonedShadowHandRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SummonedShadowHandModel());
		this.shadowRadius = 1f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
