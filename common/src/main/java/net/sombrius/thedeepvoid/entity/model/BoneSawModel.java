package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BoneSawEntity;
import software.bernie.geckolib.model.GeoModel;

public class BoneSawModel extends GeoModel<BoneSawEntity> {
	@Override
	public ResourceLocation getAnimationResource(BoneSawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/bonesaw.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BoneSawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/bonesaw.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BoneSawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
