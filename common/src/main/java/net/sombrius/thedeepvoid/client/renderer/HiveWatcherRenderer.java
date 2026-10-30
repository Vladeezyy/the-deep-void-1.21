package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.HiveWatcherModel;
import net.sombrius.thedeepvoid.entity.HiveWatcherEntity;

public class HiveWatcherRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<HiveWatcherEntity, R> {
	public HiveWatcherRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new HiveWatcherModel());
		this.shadowRadius = 0.5f;
		withScale(2f);
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
