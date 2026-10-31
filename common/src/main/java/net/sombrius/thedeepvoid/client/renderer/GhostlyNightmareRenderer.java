
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.client.model.Modelnightmare_remodel;
import net.sombrius.thedeepvoid.entity.GhostlyNightmareEntity;

public class GhostlyNightmareRenderer extends MobRenderer<GhostlyNightmareEntity, LivingEntityRenderState, Modelnightmare_remodel<LivingEntityRenderState>> {
	public GhostlyNightmareRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelnightmare_remodel(context.bakeLayer(Modelnightmare_remodel.LAYER_LOCATION)), 0f);
		this.addLayer(new RenderLayer<LivingEntityRenderState, Modelnightmare_remodel<LivingEntityRenderState>>(this) {
			final Identifier LAYER_TEXTURE = Identifier.parse("the_deep_void:textures/entities/fake_nightmare_remodel_glow.png");
			final Modelnightmare_remodel<LivingEntityRenderState> layerModel = new Modelnightmare_remodel<>(context.bakeLayer(Modelnightmare_remodel.LAYER_LOCATION));

			@Override
			public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, LivingEntityRenderState state, float yRot, float xRot) {
				this.layerModel.setupAnim(state);
				collector.submitModel(this.layerModel, state, poseStack, RenderTypes.eyes(LAYER_TEXTURE), light, LivingEntityRenderer.getOverlayCoords(state, 0), state.outlineColor);
			}
		});
	}

	@Override
	protected void scale(LivingEntityRenderState state, PoseStack poseStack) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/nightmare_remodel.png");
	}

	@Override
	protected boolean isBodyVisible(LivingEntityRenderState state) {
		return false;
	}

	@Override
	protected boolean isShaking(LivingEntityRenderState state) {
		return true;
	}
}
