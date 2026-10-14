package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.LickerEntity;
import software.bernie.geckolib.model.GeoModel;

public class LickerModel extends GeoModel<LickerEntity> {
	@Override
	public ResourceLocation getAnimationResource(LickerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/lickerremodeled.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LickerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/lickerremodeled.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LickerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
