
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.client.model.Modeloverseer_inactive;
import net.sombrius.thedeepvoid.entity.OverseerPillarEntity;

public class OverseerPillarRenderer extends MobRenderer<OverseerPillarEntity, LivingEntityRenderState, Modeloverseer_inactive<LivingEntityRenderState>> {
	public OverseerPillarRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeloverseer_inactive(context.bakeLayer(Modeloverseer_inactive.LAYER_LOCATION)), 0f);
		this.addLayer(new RenderLayer<LivingEntityRenderState, Modeloverseer_inactive<LivingEntityRenderState>>(this) {
			final Identifier LAYER_TEXTURE = Identifier.parse("the_deep_void:textures/entities/overseer_pillar.png");
			final Modeloverseer_inactive<LivingEntityRenderState> layerModel = new Modeloverseer_inactive<>(context.bakeLayer(Modeloverseer_inactive.LAYER_LOCATION));

			@Override
			public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, LivingEntityRenderState state, float yRot, float xRot) {
				this.layerModel.setupAnim(state);
				collector.submitModel(this.layerModel, state, poseStack, RenderTypes.entityCutout(LAYER_TEXTURE), light, LivingEntityRenderer.getOverlayCoords(state, 0), state.outlineColor);
			}
		});
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/overseer_pillar.png");
	}
}
