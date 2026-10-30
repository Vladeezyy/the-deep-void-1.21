package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SummonedShadowHandEntity;
import software.bernie.geckolib.model.GeoModel;

public class SummonedShadowHandModel extends GeoModel<SummonedShadowHandEntity> {
	@Override
	public ResourceLocation getAnimationResource(SummonedShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/shadowhand.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SummonedShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/shadowhand.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SummonedShadowHandEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
