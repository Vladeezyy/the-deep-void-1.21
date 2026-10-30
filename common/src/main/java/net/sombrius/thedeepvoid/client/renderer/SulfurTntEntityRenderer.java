package net.sombrius.thedeepvoid.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.client.model.Modelsulfur_tnt;
import net.sombrius.thedeepvoid.entity.SulfurTntEntityEntity;
import net.sombrius.thedeepvoid.port.PortRender;
import net.sombrius.thedeepvoid.procedures.SulfurTntEntityDisplayConditionProcedure;
import net.sombrius.thedeepvoid.procedures.SulfurTntEntityModelVisualScaleProcedure;

/** 1.21.11: the flash layer's condition and the scale procedure are computed with the render state */
public class SulfurTntEntityRenderer extends MobRenderer<SulfurTntEntityEntity, PortRender.LayerState, Modelsulfur_tnt<PortRender.LayerState>> {
	public SulfurTntEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsulfur_tnt<>(context.bakeLayer(Modelsulfur_tnt.LAYER_LOCATION)), 0f);
		this.addLayer(PortRender.layer(this, this.getModel(), Identifier.parse("the_deep_void:textures/entities/pure_white.png"), RenderTypes::eyes, state -> state.layers[0], false));
	}

	@Override
	public PortRender.LayerState createRenderState() {
		return new PortRender.LayerState();
	}

	@Override
	public void extractRenderState(SulfurTntEntityEntity entity, PortRender.LayerState state, float partialTick) {
		super.extractRenderState(entity, state, partialTick);
		state.layers[0] = SulfurTntEntityDisplayConditionProcedure.execute(entity);
		state.scale = (float) SulfurTntEntityModelVisualScaleProcedure.execute(entity);
	}

	@Override
	protected void scale(PortRender.LayerState state, PoseStack poseStack) {
		poseStack.scale(state.scale, state.scale, state.scale);
	}

	@Override
	public Identifier getTextureLocation(PortRender.LayerState state) {
		return Identifier.parse("the_deep_void:textures/entities/sulfur_tnt.png");
	}
}
