package net.sombrius.thedeepvoid.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sombrius.thedeepvoid.entity.WatcherBlockadeEntity;
import software.bernie.geckolib.model.GeoModel;

public class WatcherBlockadeModel extends GeoModel<WatcherBlockadeEntity> {
	@Override
	public ResourceLocation getAnimationResource(WatcherBlockadeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "animations/boneblockade.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(WatcherBlockadeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "geo/boneblockade.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WatcherBlockadeEntity entity) {
		return ResourceLocation.fromNamespaceAndPath("the_deep_void", "textures/entities/" + entity.getTexture() + ".png");
	}

}
