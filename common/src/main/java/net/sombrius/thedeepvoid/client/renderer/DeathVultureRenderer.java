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

import net.sombrius.thedeepvoid.entity.model.DeathVultureModel;
import net.sombrius.thedeepvoid.entity.layer.DeathVultureLayer;
import net.sombrius.thedeepvoid.entity.DeathVultureEntity;

public class DeathVultureRenderer<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<DeathVultureEntity, R> {
	public DeathVultureRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new DeathVultureModel());
		this.shadowRadius = 0.5f;
		withRenderLayer(new DeathVultureLayer<>(this));
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
