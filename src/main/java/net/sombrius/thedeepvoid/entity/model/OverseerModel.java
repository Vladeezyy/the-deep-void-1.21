package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.OverseerEntity;
import software.bernie.geckolib.model.GeoModel;

public class OverseerModel extends GeoModel<OverseerEntity> {
	@Override
	public ResourceLocation getAnimationResource(OverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/overseer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(OverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/overseer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(OverseerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
