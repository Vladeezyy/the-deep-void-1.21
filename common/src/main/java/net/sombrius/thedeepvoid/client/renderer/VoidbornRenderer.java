package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.VoidbornModel;
import net.sombrius.thedeepvoid.entity.VoidbornEntity;

public class VoidbornRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<VoidbornEntity, R> {
	public VoidbornRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new VoidbornModel());
		this.shadowRadius = 0f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
