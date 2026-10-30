package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.DoomingTombstoneEntity;
import software.bernie.geckolib.model.GeoModel;

public class DoomingTombstoneModel extends GeoModel<DoomingTombstoneEntity> {
	@Override
	public ResourceLocation getAnimationResource(DoomingTombstoneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/tombstone.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DoomingTombstoneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/tombstone.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomingTombstoneEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
