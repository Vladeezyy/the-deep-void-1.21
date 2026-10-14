package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.GaolerEntity;
import software.bernie.geckolib.model.GeoModel;

public class GaolerModel extends GeoModel<GaolerEntity> {
	@Override
	public ResourceLocation getAnimationResource(GaolerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/gaoler.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(GaolerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/gaoler.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GaolerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
