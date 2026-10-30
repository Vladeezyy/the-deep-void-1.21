package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.PitPrisonerEntity;
import software.bernie.geckolib.model.GeoModel;

public class PitPrisonerModel extends GeoModel<PitPrisonerEntity> {
	@Override
	public ResourceLocation getAnimationResource(PitPrisonerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/prisoner.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PitPrisonerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/prisoner.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PitPrisonerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
