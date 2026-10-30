
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.client.model.Modelxray;
import net.sombrius.thedeepvoid.entity.XrayEntity;

public class XrayRenderer extends MobRenderer<XrayEntity, Modelxray<XrayEntity>> {
	public XrayRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelxray(context.bakeLayer(Modelxray.LAYER_LOCATION)), 0f);
	}

	@Override
	public ResourceLocation getTextureLocation(XrayEntity entity) {
		return ResourceLocation.parse("the_deep_void:textures/entities/xray.png");
	}
}
