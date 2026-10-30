package net.sombrius.thedeepvoid.client.renderer;

import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.sombrius.thedeepvoid.entity.model.BigEyeModel;
import net.sombrius.thedeepvoid.entity.layer.BigEyeLayer;
import net.sombrius.thedeepvoid.entity.BigEyeEntity;

public class BigEyeRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<BigEyeEntity, R> {
	public BigEyeRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new BigEyeModel());
		this.shadowRadius = 0f;
		withRenderLayer(new BigEyeLayer<>(this));
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
