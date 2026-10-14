package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.PenitentEntity;
import software.bernie.geckolib.model.GeoModel;

public class PenitentModel extends GeoModel<PenitentEntity> {
	@Override
	public ResourceLocation getAnimationResource(PenitentEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/penitent.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PenitentEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/penitent.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PenitentEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
