package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.DoomingCoffinEntity;
import software.bernie.geckolib.model.GeoModel;

public class DoomingCoffinModel extends GeoModel<DoomingCoffinEntity> {
	@Override
	public ResourceLocation getAnimationResource(DoomingCoffinEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/coffinentity.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DoomingCoffinEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/coffinentity.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomingCoffinEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
