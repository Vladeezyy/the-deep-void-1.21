package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.DeathMawModel;
import net.sombrius.thedeepvoid.entity.layer.DeathMawLayer;
import net.sombrius.thedeepvoid.entity.DeathMawEntity;

public class DeathMawRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<DeathMawEntity, R> {
	public DeathMawRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new DeathMawModel());
		this.shadowRadius = 0.5f;
		withRenderLayer(new DeathMawLayer<>(this));
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
