package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.DarkSteelSawModel;
import net.sombrius.thedeepvoid.entity.DarkSteelSawEntity;

public class DarkSteelSawRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<DarkSteelSawEntity, R> {
	public DarkSteelSawRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new DarkSteelSawModel());
		this.shadowRadius = 0.5f;
		withScale(1.5f);
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
