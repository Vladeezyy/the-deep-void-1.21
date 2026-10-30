package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.client.model.Modeldark_steel_flail;
import net.sombrius.thedeepvoid.entity.DarkSteelFlailEntity;
import net.sombrius.thedeepvoid.port.PortRender;
import net.sombrius.thedeepvoid.procedures.DarkSteelFlailDisplayCondition2Procedure;
import net.sombrius.thedeepvoid.procedures.DarkSteelFlailDisplayConditionProcedure;

/** 1.21.11: the display conditions of the layers are computed with the render state */
public class DarkSteelFlailRenderer extends MobRenderer<DarkSteelFlailEntity, PortRender.LayerState, Modeldark_steel_flail<PortRender.LayerState>> {
	public DarkSteelFlailRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldark_steel_flail<>(context.bakeLayer(Modeldark_steel_flail.LAYER_LOCATION)), 0f);
		this.addLayer(PortRender.layer(this, this.getModel(), Identifier.parse("the_deep_void:textures/entities/remflail.png"), RenderTypes::entityCutoutNoCull, state -> state.layers[0], false));
		this.addLayer(PortRender.layer(this, this.getModel(), Identifier.parse("the_deep_void:textures/entities/flail.png"), RenderTypes::entityCutoutNoCull, state -> state.layers[1], false));
	}

	@Override
	public PortRender.LayerState createRenderState() {
		return new PortRender.LayerState();
	}

	@Override
	public void extractRenderState(DarkSteelFlailEntity entity, PortRender.LayerState state, float partialTick) {
		super.extractRenderState(entity, state, partialTick);
		state.layers[0] = DarkSteelFlailDisplayConditionProcedure.execute(entity);
		state.layers[1] = DarkSteelFlailDisplayCondition2Procedure.execute(entity);
	}

	@Override
	public Identifier getTextureLocation(PortRender.LayerState state) {
		return Identifier.parse("the_deep_void:textures/entities/flail.png");
	}
}
