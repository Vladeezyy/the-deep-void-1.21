
package net.sombrius.thedeepvoid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.HiveWatcherEntity;
import net.sombrius.thedeepvoid.entity.model.HiveWatcherModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HiveWatcherRenderer extends GeoEntityRenderer<HiveWatcherEntity> {
	public HiveWatcherRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new HiveWatcherModel());
		this.shadowRadius = 0.5f;
	}

	@Override
	public RenderType getRenderType(HiveWatcherEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, HiveWatcherEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		float scale = 2f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
	}

	@Override
	protected float getDeathMaxRotation(HiveWatcherEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
