
package net.sombrius.thedeepvoid.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.client.model.Modelgiant_crawler_egg;
import net.sombrius.thedeepvoid.entity.GiantBoneCrawlerEggEntity;

public class GiantBoneCrawlerEggRenderer extends MobRenderer<GiantBoneCrawlerEggEntity, Modelgiant_crawler_egg<GiantBoneCrawlerEggEntity>> {
	public GiantBoneCrawlerEggRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgiant_crawler_egg(context.bakeLayer(Modelgiant_crawler_egg.LAYER_LOCATION)), 2.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(GiantBoneCrawlerEggEntity entity) {
		return ResourceLocation.parse("the_deep_void:textures/entities/giant_crawler_egg.png");
	}
}
