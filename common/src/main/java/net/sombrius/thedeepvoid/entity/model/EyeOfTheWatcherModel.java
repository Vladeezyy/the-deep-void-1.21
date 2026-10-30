package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.EyeOfTheWatcherEntity;
import software.bernie.geckolib.model.GeoModel;

public class EyeOfTheWatcherModel extends GeoModel<EyeOfTheWatcherEntity> {
	@Override
	public ResourceLocation getAnimationResource(EyeOfTheWatcherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/eyeofthewatcher.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(EyeOfTheWatcherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/eyeofthewatcher.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EyeOfTheWatcherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
