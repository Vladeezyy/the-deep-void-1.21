package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.OverseerModel;
import net.sombrius.thedeepvoid.entity.OverseerEntity;

public class OverseerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<OverseerEntity, R> {
	public OverseerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new OverseerModel());
		this.shadowRadius = 0f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
