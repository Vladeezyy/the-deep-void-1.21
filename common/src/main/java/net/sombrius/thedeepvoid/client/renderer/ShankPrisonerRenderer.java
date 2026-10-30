package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.ShankPrisonerModel;
import net.sombrius.thedeepvoid.entity.ShankPrisonerEntity;

public class ShankPrisonerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<ShankPrisonerEntity, R> {
	public ShankPrisonerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ShankPrisonerModel());
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
