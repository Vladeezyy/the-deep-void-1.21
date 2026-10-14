package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.FalseHydraArmEntity;
import software.bernie.geckolib.model.GeoModel;

public class FalseHydraArmModel extends GeoModel<FalseHydraArmEntity> {
	@Override
	public ResourceLocation getAnimationResource(FalseHydraArmEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/falsehydraarm.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FalseHydraArmEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/falsehydraarm.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FalseHydraArmEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
