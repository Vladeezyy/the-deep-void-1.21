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

import net.sombrius.thedeepvoid.entity.model.FourEyesModel;
import net.sombrius.thedeepvoid.entity.layer.FourEyesLayer;
import net.sombrius.thedeepvoid.entity.FourEyesEntity;

public class FourEyesRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<FourEyesEntity, R> {
	public FourEyesRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new FourEyesModel());
		this.shadowRadius = 0f;
		withRenderLayer(new FourEyesLayer<>(this));
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
