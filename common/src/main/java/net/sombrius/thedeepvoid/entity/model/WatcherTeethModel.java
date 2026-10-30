package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.WatcherTeethEntity;
import software.bernie.geckolib.model.GeoModel;

public class WatcherTeethModel extends GeoModel<WatcherTeethEntity> {
	@Override
	public ResourceLocation getAnimationResource(WatcherTeethEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/watcher_teeth.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WatcherTeethEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/watcher_teeth.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WatcherTeethEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
