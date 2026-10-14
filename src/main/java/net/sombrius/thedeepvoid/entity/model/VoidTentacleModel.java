package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.VoidTentacleEntity;
import software.bernie.geckolib.model.GeoModel;

public class VoidTentacleModel extends GeoModel<VoidTentacleEntity> {
	@Override
	public ResourceLocation getAnimationResource(VoidTentacleEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/void_tentacle.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(VoidTentacleEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/void_tentacle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(VoidTentacleEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
