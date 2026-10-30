package net.sombrius.thedeepvoid.client.renderer;

import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.FoolEaterModel;
import net.sombrius.thedeepvoid.entity.layer.FoolEaterLayer;
import net.sombrius.thedeepvoid.entity.FoolEaterEntity;

public class FoolEaterRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<FoolEaterEntity, R> {
	public FoolEaterRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new FoolEaterModel());
		this.shadowRadius = 0f;
		withScale(1.4f);
		withRenderLayer(new FoolEaterLayer<>(this));
	}

	@Override
	public RenderType getRenderType(R renderState, Identifier texture) {
		return RenderTypes.entityTranslucent(texture);
	}

	@Override
	public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
		DefaultAnimations.hardcodedHeadRotation(renderPassInfo, snapshots, "head");
	}
}
