package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.DevourerModel;
import net.sombrius.thedeepvoid.entity.DevourerEntity;

public class DevourerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<DevourerEntity, R> {
	public DevourerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new DevourerModel());
		this.shadowRadius = 0.5f;
		withScale(1.1f);
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
