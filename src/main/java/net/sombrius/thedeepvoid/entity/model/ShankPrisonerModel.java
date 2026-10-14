package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.ShankPrisonerEntity;
import software.bernie.geckolib.model.GeoModel;

public class ShankPrisonerModel extends GeoModel<ShankPrisonerEntity> {
	@Override
	public ResourceLocation getAnimationResource(ShankPrisonerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/shankprisoner.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ShankPrisonerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/shankprisoner.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShankPrisonerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
