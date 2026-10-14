package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.ThumperEntityEntity;
import software.bernie.geckolib.model.GeoModel;

public class ThumperEntityModel extends GeoModel<ThumperEntityEntity> {
	@Override
	public ResourceLocation getAnimationResource(ThumperEntityEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/thumper.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(ThumperEntityEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/thumper.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ThumperEntityEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
