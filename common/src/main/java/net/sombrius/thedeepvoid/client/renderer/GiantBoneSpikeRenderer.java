
package net.sombrius.thedeepvoid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.GiantBoneSpikeEntity;
import net.sombrius.thedeepvoid.entity.model.GiantBoneSpikeModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GiantBoneSpikeRenderer extends GeoEntityRenderer<GiantBoneSpikeEntity> {
	public GiantBoneSpikeRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new GiantBoneSpikeModel());
		this.shadowRadius = 0f;
	}

	@Override
	public RenderType getRenderType(GiantBoneSpikeEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, GiantBoneSpikeEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		float scale = 1.2f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
	}

	@Override
	protected float getDeathMaxRotation(GiantBoneSpikeEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
