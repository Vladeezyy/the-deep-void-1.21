package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.client.model.Modelamalgam_rework;
import net.sombrius.thedeepvoid.entity.AmalgamEntity;
import net.sombrius.thedeepvoid.port.PortRender;
import net.sombrius.thedeepvoid.procedures.AmalgamDisplayConditionProcedure;

/** 1.21.11: the display condition of the uncovered layer is computed with the render state */
public class AmalgamRenderer extends MobRenderer<AmalgamEntity, PortRender.LayerState, Modelamalgam_rework<PortRender.LayerState>> {
	public AmalgamRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelamalgam_rework<>(context.bakeLayer(Modelamalgam_rework.LAYER_LOCATION)), 0f);
		this.addLayer(PortRender.layer(this, this.getModel(), Identifier.parse("the_deep_void:textures/entities/amalgam_rework_glow.png"), RenderTypes::eyes, state -> true, true));
		this.addLayer(PortRender.layer(this, this.getModel(), Identifier.parse("the_deep_void:textures/entities/amalgam_rework.png"), RenderTypes::entityCutoutNoCull, state -> state.layers[0], true));
	}

	@Override
	public PortRender.LayerState createRenderState() {
		return new PortRender.LayerState();
	}

	@Override
	public void extractRenderState(AmalgamEntity entity, PortRender.LayerState state, float partialTick) {
		super.extractRenderState(entity, state, partialTick);
		state.layers[0] = AmalgamDisplayConditionProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ());
	}

	@Override
	public Identifier getTextureLocation(PortRender.LayerState state) {
		return Identifier.parse("the_deep_void:textures/entities/amalgam_camo.png");
	}
}
