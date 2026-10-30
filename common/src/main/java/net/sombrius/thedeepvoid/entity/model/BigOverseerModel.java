package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BigOverseerEntity;
import software.bernie.geckolib.model.GeoModel;

public class BigOverseerModel extends GeoModel<BigOverseerEntity> {
	@Override
	public ResourceLocation getAnimationResource(BigOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/overseer3blocks.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BigOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/overseer3blocks.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BigOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
