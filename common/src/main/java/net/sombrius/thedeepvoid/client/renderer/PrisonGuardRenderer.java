package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.PrisonGuardModel;
import net.sombrius.thedeepvoid.entity.PrisonGuardEntity;

public class PrisonGuardRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<PrisonGuardEntity, R> {
	public PrisonGuardRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new PrisonGuardModel());
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
