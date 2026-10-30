package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import software.bernie.geckolib.model.GeoModel;

public class FalseHydraModel extends GeoModel<FalseHydraEntity> {
	@Override
	public ResourceLocation getAnimationResource(FalseHydraEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/falsehydra.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FalseHydraEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/falsehydra.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FalseHydraEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
