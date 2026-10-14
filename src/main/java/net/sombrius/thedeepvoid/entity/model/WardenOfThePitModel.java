package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.WardenOfThePitEntity;
import software.bernie.geckolib.model.GeoModel;

public class WardenOfThePitModel extends GeoModel<WardenOfThePitEntity> {
	@Override
	public ResourceLocation getAnimationResource(WardenOfThePitEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/wardenofthepit.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WardenOfThePitEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/wardenofthepit.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WardenOfThePitEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
