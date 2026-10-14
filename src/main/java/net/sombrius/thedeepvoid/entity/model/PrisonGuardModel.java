package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.PrisonGuardEntity;
import software.bernie.geckolib.model.GeoModel;

public class PrisonGuardModel extends GeoModel<PrisonGuardEntity> {
	@Override
	public ResourceLocation getAnimationResource(PrisonGuardEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/prisonguard.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PrisonGuardEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/prisonguard.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PrisonGuardEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
