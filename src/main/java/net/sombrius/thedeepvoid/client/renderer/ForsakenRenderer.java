
package net.sombrius.thedeepvoid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.ForsakenEntity;
import net.sombrius.thedeepvoid.entity.layer.ForsakenLayer;
import net.sombrius.thedeepvoid.entity.model.ForsakenModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ForsakenRenderer extends GeoEntityRenderer<ForsakenEntity> {
	public ForsakenRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new ForsakenModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new ForsakenLayer(this));
	}

	@Override
	public RenderType getRenderType(ForsakenEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, ForsakenEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
	}
}
