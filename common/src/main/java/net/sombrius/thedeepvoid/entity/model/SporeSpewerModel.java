package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SporeSpewerEntity;
import software.bernie.geckolib.model.GeoModel;

public class SporeSpewerModel extends GeoModel<SporeSpewerEntity> {
	@Override
	public ResourceLocation getAnimationResource(SporeSpewerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/sporespewer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SporeSpewerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/sporespewer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SporeSpewerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
