package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.FalseHydraSingingEntity;
import software.bernie.geckolib.model.GeoModel;

public class FalseHydraSingingModel extends GeoModel<FalseHydraSingingEntity> {
	@Override
	public ResourceLocation getAnimationResource(FalseHydraSingingEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/falsehydra.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(FalseHydraSingingEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/falsehydra.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(FalseHydraSingingEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
