package net.sombrius.thedeepvoid.utils;

import net.minecraft.util.FastColor;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;

public class AnimUtils {
	/**
	 * The player's arms over a gun's arm bones (first person) draw from a buffer source of their own: asking the item's
	 * source for another render type closes the buffer the gun is drawing into (1.21; MCreator's 1.20.1 renderers took
	 * the arm and the sleeve buffer at once and crashed with "Not building!" as soon as a gun was held).
	 */
	private static final MultiBufferSource.BufferSource ARMS = MultiBufferSource.immediate(new ByteBufferBuilder(4096));

	public static void renderArm(ModelPart arm, ModelPart sleeve, GeoBone bone, PoseStack stack, ResourceLocation skin, int packedLightIn, float alpha) {
		renderPartOverBone(arm, bone, stack, ARMS.getBuffer(RenderType.entitySolid(skin)), packedLightIn, OverlayTexture.NO_OVERLAY, alpha);
		renderPartOverBone(sleeve, bone, stack, ARMS.getBuffer(RenderType.entityTranslucent(skin)), packedLightIn, OverlayTexture.NO_OVERLAY, alpha);
		ARMS.endBatch();
	}

	public static void renderPartOverBone(ModelPart model, GeoBone bone, PoseStack stack, VertexConsumer buffer, int packedLightIn, int packedOverlayIn, float alpha) {
		renderPartOverBone(model, bone, stack, buffer, packedLightIn, packedOverlayIn, 1.0f, 1.0f, 1.0f, alpha);
	}

	public static void renderPartOverBone(ModelPart model, GeoBone bone, PoseStack stack, VertexConsumer buffer, int packedLightIn, int packedOverlayIn, float r, float g, float b, float a) {
		setupModelFromBone(model, bone);
		model.render(stack, buffer, packedLightIn, packedOverlayIn, FastColor.ARGB32.colorFromFloat(a, r, g, b));
	}

	public static void setupModelFromBone(ModelPart model, GeoBone bone) {
		model.setPos(bone.getPivotX(), bone.getPivotY(), bone.getPivotZ());
		model.xRot = 0.0f;
		model.yRot = 0.0f;
		model.zRot = 0.0f;
	}
}
