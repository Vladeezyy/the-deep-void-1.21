package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.SawThrowerEntity;
import software.bernie.geckolib.model.GeoModel;

public class SawThrowerModel extends GeoModel<SawThrowerEntity> {
	@Override
	public ResourceLocation getAnimationResource(SawThrowerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/saw_thrower.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(SawThrowerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/saw_thrower.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SawThrowerEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
