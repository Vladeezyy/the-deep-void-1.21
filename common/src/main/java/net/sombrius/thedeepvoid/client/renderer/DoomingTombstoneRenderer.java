package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.DoomingTombstoneModel;
import net.sombrius.thedeepvoid.entity.layer.DoomingTombstoneLayer;
import net.sombrius.thedeepvoid.entity.DoomingTombstoneEntity;

public class DoomingTombstoneRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<DoomingTombstoneEntity, R> {
	public DoomingTombstoneRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new DoomingTombstoneModel());
		this.shadowRadius = 0.5f;
		withRenderLayer(new DoomingTombstoneLayer<>(this));
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}
}
