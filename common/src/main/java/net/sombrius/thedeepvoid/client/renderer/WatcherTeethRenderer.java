package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.WatcherTeethModel;
import net.sombrius.thedeepvoid.entity.WatcherTeethEntity;

public class WatcherTeethRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<WatcherTeethEntity, R> {
	public WatcherTeethRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WatcherTeethModel());
		this.shadowRadius = 0f;
		withScale(1.5f);
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
