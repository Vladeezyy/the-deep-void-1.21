package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.SporeSpewerModel;
import net.sombrius.thedeepvoid.entity.SporeSpewerEntity;

public class SporeSpewerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<SporeSpewerEntity, R> {
	public SporeSpewerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SporeSpewerModel());
		this.shadowRadius = 0.5f;
		withScale(1.14f);
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
