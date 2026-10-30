package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.FleshCubeModel;
import net.sombrius.thedeepvoid.entity.FleshCubeEntity;

public class FleshCubeRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<FleshCubeEntity, R> {
	public FleshCubeRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new FleshCubeModel());
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
