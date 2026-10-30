package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.FleshFangsEntity;
import software.bernie.geckolib.model.GeoModel;

public class FleshFangsModel extends GeoModel<FleshFangsEntity> {
	@Override
	public ResourceLocation getAnimationResource(FleshFangsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/fleshfangs.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FleshFangsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/fleshfangs.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FleshFangsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
