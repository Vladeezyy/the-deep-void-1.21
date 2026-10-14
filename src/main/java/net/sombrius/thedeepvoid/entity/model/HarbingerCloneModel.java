package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.HarbingerCloneEntity;
import software.bernie.geckolib.model.GeoModel;

public class HarbingerCloneModel extends GeoModel<HarbingerCloneEntity> {
	@Override
	public ResourceLocation getAnimationResource(HarbingerCloneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/harbinger.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HarbingerCloneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/harbinger.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HarbingerCloneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
