package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SummonedDevourerEntity;
import software.bernie.geckolib.model.GeoModel;

public class SummonedDevourerModel extends GeoModel<SummonedDevourerEntity> {
	@Override
	public ResourceLocation getAnimationResource(SummonedDevourerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/devourer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SummonedDevourerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/devourer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SummonedDevourerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
