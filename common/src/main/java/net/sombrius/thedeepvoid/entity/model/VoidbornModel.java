package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.VoidbornEntity;
import software.bernie.geckolib.model.GeoModel;

public class VoidbornModel extends GeoModel<VoidbornEntity> {
	@Override
	public ResourceLocation getAnimationResource(VoidbornEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/cavenightmare.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(VoidbornEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/cavenightmare.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(VoidbornEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
