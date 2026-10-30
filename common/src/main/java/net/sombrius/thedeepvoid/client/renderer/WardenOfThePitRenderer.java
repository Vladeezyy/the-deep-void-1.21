package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.WardenOfThePitModel;
import net.sombrius.thedeepvoid.entity.layer.WardenOfThePitLayer;
import net.sombrius.thedeepvoid.entity.WardenOfThePitEntity;

public class WardenOfThePitRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<WardenOfThePitEntity, R> {
	public WardenOfThePitRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WardenOfThePitModel());
		this.shadowRadius = 0f;
		withScale(8f);
		withRenderLayer(new WardenOfThePitLayer<>(this));
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
