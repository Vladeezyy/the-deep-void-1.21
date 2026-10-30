package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

import net.sombrius.thedeepvoid.entity.GhostEntity;
import net.sombrius.thedeepvoid.port.PortRender;

/** 1.21.11: the glowing body is a layer of the (hidden) model drawn with the eyes render type */
public class GhostRenderer extends HumanoidMobRenderer<GhostEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {
	public GhostRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0f);
		this.addLayer(new HumanoidArmorLayer<>(this, ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new), context.getEquipmentRenderer()));
		this.addLayer(PortRender.layer(this, this.getModel(), Identifier.parse("the_deep_void:textures/entities/ghost.png"), RenderTypes::eyes, state -> true, false));
	}

	@Override
	public HumanoidRenderState createRenderState() {
		return new HumanoidRenderState();
	}

	@Override
	public Identifier getTextureLocation(HumanoidRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/ghost.png");
	}

	@Override
	protected boolean isBodyVisible(HumanoidRenderState state) {
		return false;
	}
}
