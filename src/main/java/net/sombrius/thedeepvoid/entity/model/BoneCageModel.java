package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BoneCageEntity;
import software.bernie.geckolib.model.GeoModel;

public class BoneCageModel extends GeoModel<BoneCageEntity> {
	@Override
	public ResourceLocation getAnimationResource(BoneCageEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/bone_cage.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BoneCageEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/bone_cage.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BoneCageEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
