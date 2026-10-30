package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.DevourerEntity;
import software.bernie.geckolib.model.GeoModel;

public class DevourerModel extends GeoModel<DevourerEntity> {
	@Override
	public ResourceLocation getAnimationResource(DevourerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/devourer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DevourerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/devourer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DevourerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
