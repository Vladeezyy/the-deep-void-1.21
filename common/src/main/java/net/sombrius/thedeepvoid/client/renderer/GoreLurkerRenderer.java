package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.GoreLurkerModel;
import net.sombrius.thedeepvoid.entity.GoreLurkerEntity;

public class GoreLurkerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<GoreLurkerEntity, R> {
	public GoreLurkerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new GoreLurkerModel());
		this.shadowRadius = 0f;
		withScale(1.2f);
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
