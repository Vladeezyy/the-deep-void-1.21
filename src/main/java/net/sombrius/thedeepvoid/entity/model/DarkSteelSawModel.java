package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.DarkSteelSawEntity;
import software.bernie.geckolib.model.GeoModel;

public class DarkSteelSawModel extends GeoModel<DarkSteelSawEntity> {
	@Override
	public ResourceLocation getAnimationResource(DarkSteelSawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/darksteelsaw.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DarkSteelSawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/darksteelsaw.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DarkSteelSawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
