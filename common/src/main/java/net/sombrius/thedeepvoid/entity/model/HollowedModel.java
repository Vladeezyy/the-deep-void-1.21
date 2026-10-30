package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.HollowedEntity;
import software.bernie.geckolib.model.GeoModel;

public class HollowedModel extends GeoModel<HollowedEntity> {
	@Override
	public ResourceLocation getAnimationResource(HollowedEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/hollowed.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HollowedEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/hollowed.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HollowedEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
