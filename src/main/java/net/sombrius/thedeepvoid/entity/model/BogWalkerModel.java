package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BogWalkerEntity;
import software.bernie.geckolib.model.GeoModel;

public class BogWalkerModel extends GeoModel<BogWalkerEntity> {
	@Override
	public ResourceLocation getAnimationResource(BogWalkerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/bogwalker.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BogWalkerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/bogwalker.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BogWalkerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
