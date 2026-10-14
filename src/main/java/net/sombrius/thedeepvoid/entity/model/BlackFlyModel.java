package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BlackFlyEntity;
import software.bernie.geckolib.model.GeoModel;

public class BlackFlyModel extends GeoModel<BlackFlyEntity> {
	@Override
	public ResourceLocation getAnimationResource(BlackFlyEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/voidfly.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BlackFlyEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/voidfly.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BlackFlyEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
