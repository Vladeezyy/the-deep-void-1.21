package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.MaggotEntity;
import software.bernie.geckolib.model.GeoModel;

public class MaggotModel extends GeoModel<MaggotEntity> {
	@Override
	public ResourceLocation getAnimationResource(MaggotEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/maggot.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(MaggotEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/maggot.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MaggotEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
