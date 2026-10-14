package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.VoidbornTentaclesEntity;
import software.bernie.geckolib.model.GeoModel;

public class VoidbornTentaclesModel extends GeoModel<VoidbornTentaclesEntity> {
	@Override
	public ResourceLocation getAnimationResource(VoidbornTentaclesEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/voidborntentacles.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(VoidbornTentaclesEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/voidborntentacles.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(VoidbornTentaclesEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
