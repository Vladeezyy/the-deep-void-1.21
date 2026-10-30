
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sombrius.thedeepvoid.client.model.Modeldark_steel_flail;
import net.sombrius.thedeepvoid.entity.ExecutionerDarkSteelFlailEntity;

public class ExecutionerDarkSteelFlailRenderer extends MobRenderer<ExecutionerDarkSteelFlailEntity, LivingEntityRenderState, Modeldark_steel_flail<LivingEntityRenderState>> {
	public ExecutionerDarkSteelFlailRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldark_steel_flail(context.bakeLayer(Modeldark_steel_flail.LAYER_LOCATION)), 0f);
	}

	@Override
	public LivingEntityRenderState createRenderState() {
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTextureLocation(LivingEntityRenderState state) {
		return Identifier.parse("the_deep_void:textures/entities/flail.png");
	}
}
