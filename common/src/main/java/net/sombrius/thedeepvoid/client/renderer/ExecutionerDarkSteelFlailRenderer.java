
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.client.model.Modeldark_steel_flail;
import net.sombrius.thedeepvoid.entity.ExecutionerDarkSteelFlailEntity;

public class ExecutionerDarkSteelFlailRenderer extends MobRenderer<ExecutionerDarkSteelFlailEntity, Modeldark_steel_flail<ExecutionerDarkSteelFlailEntity>> {
	public ExecutionerDarkSteelFlailRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldark_steel_flail(context.bakeLayer(Modeldark_steel_flail.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(ExecutionerDarkSteelFlailEntity entity) {
		return ResourceLocation.parse("the_deep_void:textures/entities/flail.png");
	}
}
