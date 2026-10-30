package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.BoneSawModel;
import net.sombrius.thedeepvoid.entity.BoneSawEntity;

public class BoneSawRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<BoneSawEntity, R> {
	public BoneSawRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new BoneSawModel());
		this.shadowRadius = 0f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
