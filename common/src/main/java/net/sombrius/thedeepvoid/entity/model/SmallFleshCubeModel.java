package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SmallFleshCubeEntity;
import software.bernie.geckolib.model.GeoModel;

public class SmallFleshCubeModel extends GeoModel<SmallFleshCubeEntity> {
	@Override
	public ResourceLocation getAnimationResource(SmallFleshCubeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/smallfleshcube.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SmallFleshCubeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/smallfleshcube.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SmallFleshCubeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
