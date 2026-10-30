package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.SleepingPrimordialCrawlerModel;
import net.sombrius.thedeepvoid.entity.SleepingPrimordialCrawlerEntity;

public class SleepingPrimordialCrawlerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<SleepingPrimordialCrawlerEntity, R> {
	public SleepingPrimordialCrawlerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SleepingPrimordialCrawlerModel());
		this.shadowRadius = 2.8f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
