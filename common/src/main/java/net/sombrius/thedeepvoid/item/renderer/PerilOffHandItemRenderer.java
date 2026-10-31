package net.sombrius.thedeepvoid.item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.sombrius.thedeepvoid.item.PerilOffHandItem;
import net.sombrius.thedeepvoid.item.model.PerilOffHandItemModel;
import net.sombrius.thedeepvoid.utils.AnimUtils;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.RenderUtil;

public class PerilOffHandItemRenderer extends GeoItemRenderer<PerilOffHandItem> {
	public PerilOffHandItemRenderer() {
		super(new PerilOffHandItemModel());
	}

	@Override
	public RenderType getRenderType(PerilOffHandItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	private static final float SCALE_RECIPROCAL = 1.0f / 16.0f;
	protected boolean renderArms = false;
	protected MultiBufferSource currentBuffer;
	protected RenderType renderType;
	public ItemDisplayContext transformType;
	protected PerilOffHandItem animatable;
	private final Set<String> hiddenBones = new HashSet<>();
	private final Set<String> suppressedBones = new HashSet<>();

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
		this.transformType = transformType;
		if (this.animatable != null)
			this.animatable.getTransformType(transformType);
		super.renderByItem(stack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
	}

	@Override
	public void actuallyRender(PoseStack matrixStackIn, PerilOffHandItem animatable, BakedGeoModel model, RenderType type, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, boolean isRenderer, float partialTicks, int packedLightIn,
			int packedOverlayIn, int color) {
		this.currentBuffer = renderTypeBuffer;
		this.renderType = type;
		this.animatable = animatable;
		super.actuallyRender(matrixStackIn, animatable, model, type, renderTypeBuffer, vertexBuilder, isRenderer, partialTicks, packedLightIn, packedOverlayIn, color);
		if (this.renderArms) {
			this.renderArms = false;
		}
	}

	@Override
	public void renderRecursively(PoseStack stack, PerilOffHandItem animatable, GeoBone bone, RenderType type, MultiBufferSource buffer, VertexConsumer bufferIn, boolean isReRender, float partialTick, int packedLightIn, int packedOverlayIn,
			int color) {
		Minecraft mc = Minecraft.getInstance();
		String name = bone.getName();
		boolean renderingArms = false;
		if (name.equals("RightArm") || name.equals("LeftArm")) {
			bone.setHidden(true);
			renderingArms = true;
		} else {
			bone.setHidden(this.hiddenBones.contains(name));
		}
		if (this.transformType.firstPerson() && renderingArms) {
			AbstractClientPlayer player = mc.player;
			float armsAlpha = player.isInvisible() ? 0.15f : 1.0f;
			PlayerRenderer playerRenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);
			PlayerModel<AbstractClientPlayer> model = playerRenderer.getModel();
			stack.pushPose();
			RenderUtil.translateMatrixToBone(stack, bone);
			RenderUtil.translateToPivotPoint(stack, bone);
			RenderUtil.rotateMatrixAroundBone(stack, bone);
			RenderUtil.scaleMatrixForBone(stack, bone);
			RenderUtil.translateAwayFromPivotPoint(stack, bone);
			ResourceLocation loc = player.getSkin().texture();
			if (name.equals("RightArm")) {
				stack.translate(-1.0f * SCALE_RECIPROCAL, 2.0f * SCALE_RECIPROCAL, 0.0f);
				AnimUtils.renderArm(model.leftArm, model.leftSleeve, bone, stack, loc, packedLightIn, armsAlpha);
			} else if (name.equals("LeftArm")) {
				stack.translate(1.0f * SCALE_RECIPROCAL, 2.0f * SCALE_RECIPROCAL, 0.0f);
				AnimUtils.renderArm(model.rightArm, model.rightSleeve, bone, stack, loc, packedLightIn, armsAlpha);
			}
			stack.popPose();
		}
		super.renderRecursively(stack, animatable, bone, type, buffer, bufferIn, isReRender, partialTick, packedLightIn, packedOverlayIn, color);
	}

	@Override
	public ResourceLocation getTextureLocation(PerilOffHandItem instance) {
		return super.getTextureLocation(instance);
	}
}
