package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.WeaverOfSoulsBossModel;
import net.sombrius.thedeepvoid.entity.layer.WeaverOfSoulsBossLayer;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;

public class WeaverOfSoulsBossRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<WeaverOfSoulsBossEntity, R> {
	public WeaverOfSoulsBossRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WeaverOfSoulsBossModel());
		this.shadowRadius = 1.8f;
		withRenderLayer(new WeaverOfSoulsBossLayer<>(this));
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
