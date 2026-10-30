package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.SmallFleshCubeModel;
import net.sombrius.thedeepvoid.entity.SmallFleshCubeEntity;

public class SmallFleshCubeRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<SmallFleshCubeEntity, R> {
	public SmallFleshCubeRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SmallFleshCubeModel());
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
