package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.BuiltOverseerEntity;
import software.bernie.geckolib.model.GeoModel;

public class BuiltOverseerModel extends GeoModel<BuiltOverseerEntity> {
	@Override
	public ResourceLocation getAnimationResource(BuiltOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/overseer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(BuiltOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/overseer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BuiltOverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
