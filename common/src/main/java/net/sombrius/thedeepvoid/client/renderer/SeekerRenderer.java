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

import net.sombrius.thedeepvoid.entity.model.SeekerModel;
import net.sombrius.thedeepvoid.entity.layer.SeekerLayer;
import net.sombrius.thedeepvoid.entity.SeekerEntity;

public class SeekerRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<SeekerEntity, R> {
	public SeekerRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SeekerModel());
		this.shadowRadius = 1f;
		withRenderLayer(new SeekerLayer<>(this));
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
