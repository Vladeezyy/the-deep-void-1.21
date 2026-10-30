package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.FleshWormModel;
import net.sombrius.thedeepvoid.entity.FleshWormEntity;

public class FleshWormRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<FleshWormEntity, R> {
	public FleshWormRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new FleshWormModel());
		this.shadowRadius = 0f;
		withScale(1.4f);
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
