package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.HiveBrainEntity;
import software.bernie.geckolib.model.GeoModel;

public class HiveBrainModel extends GeoModel<HiveBrainEntity> {
	@Override
	public ResourceLocation getAnimationResource(HiveBrainEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/hive_brain.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(HiveBrainEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/hive_brain.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HiveBrainEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
