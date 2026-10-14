package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.GrabbingHandsEntity;
import software.bernie.geckolib.model.GeoModel;

public class GrabbingHandsModel extends GeoModel<GrabbingHandsEntity> {
	@Override
	public ResourceLocation getAnimationResource(GrabbingHandsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/grabbinghands.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GrabbingHandsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/grabbinghands.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GrabbingHandsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
