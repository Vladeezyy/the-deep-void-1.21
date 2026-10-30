package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.BoneCageClosedModel;
import net.sombrius.thedeepvoid.entity.BoneCageClosedEntity;

public class BoneCageClosedRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<BoneCageClosedEntity, R> {
	public BoneCageClosedRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new BoneCageClosedModel());
		this.shadowRadius = 0f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
