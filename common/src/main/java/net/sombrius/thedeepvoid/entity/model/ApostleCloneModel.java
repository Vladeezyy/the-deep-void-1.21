package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.ApostleCloneEntity;
import software.bernie.geckolib.model.GeoModel;

public class ApostleCloneModel extends GeoModel<ApostleCloneEntity> {
	@Override
	public ResourceLocation getAnimationResource(ApostleCloneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/apostleofcatastrophe.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ApostleCloneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/apostleofcatastrophe.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ApostleCloneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
