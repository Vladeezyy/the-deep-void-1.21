package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.FleshWormEntity;
import software.bernie.geckolib.model.GeoModel;

public class FleshWormModel extends GeoModel<FleshWormEntity> {
	@Override
	public ResourceLocation getAnimationResource(FleshWormEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/fleshwormnew.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FleshWormEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/fleshwormnew.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FleshWormEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
