package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.FleshCubeEntity;
import software.bernie.geckolib.model.GeoModel;

public class FleshCubeModel extends GeoModel<FleshCubeEntity> {
	@Override
	public ResourceLocation getAnimationResource(FleshCubeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/fleshcube.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FleshCubeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/fleshcube.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FleshCubeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
