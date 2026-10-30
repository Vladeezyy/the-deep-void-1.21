package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.LastingShadowHandEntity;
import software.bernie.geckolib.model.GeoModel;

public class LastingShadowHandModel extends GeoModel<LastingShadowHandEntity> {
	@Override
	public ResourceLocation getAnimationResource(LastingShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/shadowhand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LastingShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/shadowhand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LastingShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
