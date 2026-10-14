package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.PrimordialBoneCrawlerEntity;
import software.bernie.geckolib.model.GeoModel;

public class PrimordialBoneCrawlerModel extends GeoModel<PrimordialBoneCrawlerEntity> {
	@Override
	public ResourceLocation getAnimationResource(PrimordialBoneCrawlerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/primordialbonecrawler.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PrimordialBoneCrawlerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/primordialbonecrawler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PrimordialBoneCrawlerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
