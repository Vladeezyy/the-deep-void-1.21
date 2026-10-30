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
