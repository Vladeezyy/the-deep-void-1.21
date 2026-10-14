package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.HivemindTentaclesEntity;
import software.bernie.geckolib.model.GeoModel;

public class HivemindTentaclesModel extends GeoModel<HivemindTentaclesEntity> {
	@Override
	public ResourceLocation getAnimationResource(HivemindTentaclesEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/flesh_tentacles.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HivemindTentaclesEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/flesh_tentacles.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HivemindTentaclesEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
