package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.HiveWatcherEntity;
import software.bernie.geckolib.model.GeoModel;

public class HiveWatcherModel extends GeoModel<HiveWatcherEntity> {
	@Override
	public ResourceLocation getAnimationResource(HiveWatcherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/hivewatcher.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HiveWatcherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/hivewatcher.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HiveWatcherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
