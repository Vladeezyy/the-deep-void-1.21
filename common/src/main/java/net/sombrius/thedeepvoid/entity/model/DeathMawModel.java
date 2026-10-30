package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.DeathMawEntity;
import software.bernie.geckolib.model.GeoModel;

public class DeathMawModel extends GeoModel<DeathMawEntity> {
	@Override
	public ResourceLocation getAnimationResource(DeathMawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/deathmaw.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(DeathMawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/deathmaw.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DeathMawEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
