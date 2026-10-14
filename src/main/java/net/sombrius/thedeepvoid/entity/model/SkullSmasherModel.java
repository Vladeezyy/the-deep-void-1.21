package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SkullSmasherEntity;
import software.bernie.geckolib.model.GeoModel;

public class SkullSmasherModel extends GeoModel<SkullSmasherEntity> {
	@Override
	public ResourceLocation getAnimationResource(SkullSmasherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/skull_smasher.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SkullSmasherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/skull_smasher.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SkullSmasherEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
