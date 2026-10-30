package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.sombrius.thedeepvoid.client.model.Modelambrosia_spit;
import net.sombrius.thedeepvoid.entity.AmbrosiaSpitEntity;

public class AmbrosiaSpitRenderer extends EntityRenderer<AmbrosiaSpitEntity, net.sombrius.thedeepvoid.port.PortRender.RotatedState> {
	private static final Identifier texture = Identifier.parse("the_deep_void:textures/entities/ambrosia_spit.png");
	private final Modelambrosia_spit model;

	public AmbrosiaSpitRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modelambrosia_spit(context.bakeLayer(Modelambrosia_spit.LAYER_LOCATION));
	}

	@Override
	public net.sombrius.thedeepvoid.port.PortRender.RotatedState createRenderState() {
		return new net.sombrius.thedeepvoid.port.PortRender.RotatedState();
	}

	@Override
	public void extractRenderState(AmbrosiaSpitEntity entity, net.sombrius.thedeepvoid.port.PortRender.RotatedState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.yRot = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
		state.xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
	}

	@Override
	public void submit(net.sombrius.thedeepvoid.port.PortRender.RotatedState state, PoseStack poseStack, SubmitNodeCollector collector, net.minecraft.client.renderer.state.level.CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + state.xRot));
		collector.submitModel(model, state, poseStack, RenderTypes.entityCutout(texture), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
		poseStack.popPose();
		super.submit(state, poseStack, collector, camera);
	}

	public Identifier getTextureLocation(AmbrosiaSpitEntity entity) {
		return texture;
	}
}
