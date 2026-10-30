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

import net.sombrius.thedeepvoid.entity.model.StalkerModel;
import net.sombrius.thedeepvoid.entity.layer.StalkerLayer;
import net.sombrius.thedeepvoid.entity.StalkerEntity;

public class StalkerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<StalkerEntity, R> {
	public StalkerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new StalkerModel());
		this.shadowRadius = 0.5f;
		withRenderLayer(new StalkerLayer<>(this));
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
