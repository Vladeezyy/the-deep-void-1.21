package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SleepingPrimordialCrawlerEntity;
import software.bernie.geckolib.model.GeoModel;

public class SleepingPrimordialCrawlerModel extends GeoModel<SleepingPrimordialCrawlerEntity> {
	@Override
	public ResourceLocation getAnimationResource(SleepingPrimordialCrawlerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/primordialbonecrawler.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SleepingPrimordialCrawlerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/primordialbonecrawler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SleepingPrimordialCrawlerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
