package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BuiltBigOverseerEntity;
import software.bernie.geckolib.model.GeoModel;

public class BuiltBigOverseerModel extends GeoModel<BuiltBigOverseerEntity> {
	@Override
	public ResourceLocation getAnimationResource(BuiltBigOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/overseer3blocks.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BuiltBigOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/overseer3blocks.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BuiltBigOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
