package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SummonedHiveFangsEntity;
import software.bernie.geckolib.model.GeoModel;

public class SummonedHiveFangsModel extends GeoModel<SummonedHiveFangsEntity> {
	@Override
	public ResourceLocation getAnimationResource(SummonedHiveFangsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/hivefangs.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SummonedHiveFangsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/hivefangs.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SummonedHiveFangsEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
