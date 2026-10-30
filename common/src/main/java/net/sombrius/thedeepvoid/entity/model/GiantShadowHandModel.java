package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.GiantShadowHandEntity;
import software.bernie.geckolib.model.GeoModel;

public class GiantShadowHandModel extends GeoModel<GiantShadowHandEntity> {
	@Override
	public ResourceLocation getAnimationResource(GiantShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/giantshadowhand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GiantShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/giantshadowhand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GiantShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
