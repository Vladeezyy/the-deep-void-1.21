
package net.sombrius.thedeepvoid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.MultipleEyesEntity;
import net.sombrius.thedeepvoid.entity.layer.MultipleEyesLayer;
import net.sombrius.thedeepvoid.entity.model.MultipleEyesModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MultipleEyesRenderer extends GeoEntityRenderer<MultipleEyesEntity> {
	public MultipleEyesRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new MultipleEyesModel());
		this.shadowRadius = 0f;
		this.addRenderLayer(new MultipleEyesLayer(this));
	}

	@Override
	public RenderType getRenderType(MultipleEyesEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, MultipleEyesEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
	}
}
