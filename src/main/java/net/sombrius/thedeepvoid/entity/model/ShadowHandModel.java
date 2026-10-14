package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.ShadowHandEntity;
import software.bernie.geckolib.model.GeoModel;

public class ShadowHandModel extends GeoModel<ShadowHandEntity> {
	@Override
	public ResourceLocation getAnimationResource(ShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/shadowhand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/shadowhand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
