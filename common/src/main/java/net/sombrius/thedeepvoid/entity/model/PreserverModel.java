package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.PreserverEntity;
import software.bernie.geckolib.model.GeoModel;

public class PreserverModel extends GeoModel<PreserverEntity> {
	@Override
	public ResourceLocation getAnimationResource(PreserverEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/preserver.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PreserverEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/preserver.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PreserverEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
