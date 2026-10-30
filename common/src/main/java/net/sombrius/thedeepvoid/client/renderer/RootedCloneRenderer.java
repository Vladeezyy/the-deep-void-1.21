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

import net.sombrius.thedeepvoid.entity.model.RootedCloneModel;
import net.sombrius.thedeepvoid.entity.layer.RootedCloneLayer;
import net.sombrius.thedeepvoid.entity.RootedCloneEntity;

public class RootedCloneRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<RootedCloneEntity, R> {
	public RootedCloneRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new RootedCloneModel());
		this.shadowRadius = 0f;
		withRenderLayer(new RootedCloneLayer<>(this));
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
