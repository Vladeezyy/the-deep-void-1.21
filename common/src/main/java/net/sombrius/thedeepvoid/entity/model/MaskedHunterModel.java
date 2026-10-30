package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.MaskedHunterEntity;
import software.bernie.geckolib.model.GeoModel;

public class MaskedHunterModel extends GeoModel<MaskedHunterEntity> {
	@Override
	public ResourceLocation getAnimationResource(MaskedHunterEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/huntertrue.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MaskedHunterEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/huntertrue.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MaskedHunterEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
