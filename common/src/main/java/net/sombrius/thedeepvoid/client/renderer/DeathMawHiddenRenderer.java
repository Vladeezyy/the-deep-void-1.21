
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
import net.sombrius.thedeepvoid.client.model.Modeldeath_maw_hidden;
import net.sombrius.thedeepvoid.entity.DeathMawHiddenEntity;

public class DeathMawHiddenRenderer extends MobRenderer<DeathMawHiddenEntity, LivingEntityRenderState, Modeldeath_maw_hidden<LivingEntityRenderState>> {
	public DeathMawHiddenRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldeath_maw_hidden(context.bakeLayer(Modeldeath_maw_hidden.LAYER_LOCATION)), 0f);
		this.addLayer(new RenderLayer<LivingEntityRenderState, Modeldeath_maw_hidden<LivingEntityRenderState>>(this) {
			final Identifier LAYER_TEXTURE = Identifier.parse("the_deep_void:textures/entities/death_maw_glow.png");
			final Modeldeath_maw_hidden<LivingEntityRenderState> layerModel = new Modeldeath_maw_hidden<>(context.bakeLayer(Modeldeath_maw_hidden.LAYER_LOCATION));

			@Override
			public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, LivingEntityRenderState state, float yRot, float xRot) {
				this.layerModel.setupAnim(state);
				collector.submitModel(this.layerModel, state, poseStack, RenderTypes.eyes(LAYER_TEXTURE), light, LivingEntityRenderer.getOverlayCoords(state, 0), state.outlineColor, null);
			}
		});
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/death_maw.png");
	}
}
