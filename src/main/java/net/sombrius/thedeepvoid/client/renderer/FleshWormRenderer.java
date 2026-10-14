
package net.sombrius.thedeepvoid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.FleshWormEntity;
import net.sombrius.thedeepvoid.entity.model.FleshWormModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FleshWormRenderer extends GeoEntityRenderer<FleshWormEntity> {
	public FleshWormRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new FleshWormModel());
		this.shadowRadius = 0f;
	}

	@Override
	public RenderType getRenderType(FleshWormEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, FleshWormEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		float scale = 1.4f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
	}

	@Override
	protected float getDeathMaxRotation(FleshWormEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
